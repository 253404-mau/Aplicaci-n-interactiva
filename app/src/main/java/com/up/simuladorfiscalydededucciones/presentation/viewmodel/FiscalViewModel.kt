package com.up.simuladorfiscalydededucciones.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.up.simuladorfiscalydededucciones.data.model.Deduccion
import com.up.simuladorfiscalydededucciones.data.model.SimulationHistoryItem
import com.up.simuladorfiscalydededucciones.data.repository.FiscalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FiscalViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FiscalRepository(application)
    private val tarifas = repository.getTarifasIsr()

    private val _dashboardUiState = MutableStateFlow(DashboardUiState())
    val dashboardUiState: StateFlow<DashboardUiState> = _dashboardUiState.asStateFlow()

    private val _simulationUiState = MutableStateFlow(SimulationUiState())
    val simulationUiState: StateFlow<SimulationUiState> = _simulationUiState.asStateFlow()

    private val localeMx = Locale.forLanguageTag("es-MX")
    private val currencyFormat = NumberFormat.getCurrencyInstance(localeMx).apply {
        maximumFractionDigits = 2
    }

    init {
        observeLastSimulation()
    }

    private fun observeLastSimulation() {
        viewModelScope.launch {
            repository.lastSimulationFlow.collect { (netSalary, isr, date) ->
                val netFormatted = currencyFormat.format(netSalary) + " MXN"
                val isrFormatted = currencyFormat.format(isr) + " MXN"

                val historyList = if (netSalary > 0.0) {
                    listOf(
                        SimulationHistoryItem(
                            id = "1",
                            sueldoNeto = netSalary,
                            isrRetenido = isr,
                            fecha = date.ifEmpty { getCurrentDate() }
                        )
                    )
                } else {
                    emptyList()
                }

                _dashboardUiState.update {
                    it.copy(
                        ultimoSueldoNetoFormateado = netFormatted,
                        ultimoIsrFormateado = isrFormatted,
                        ultimaFecha = date,
                        historialSimulaciones = historyList
                    )
                }
            }
        }
    }

    fun onIngresoBrutoChanged(value: String) {
        _simulationUiState.update { it.copy(ingresoBrutoInput = value, mensajeError = null) }
    }

    fun onNuevoConceptoChanged(value: String) {
        _simulationUiState.update { it.copy(nuevoConceptoInput = value) }
    }

    fun onNuevoMontoChanged(value: String) {
        _simulationUiState.update { it.copy(nuevoMontoInput = value) }
    }

    fun agregarDeduccion() {
        val currentState = _simulationUiState.value
        val concepto = currentState.nuevoConceptoInput.trim()
        val monto = currentState.nuevoMontoInput.toDoubleOrNull()

        if (concepto.isEmpty() || monto == null || monto <= 0.0) {
            _simulationUiState.update { it.copy(mensajeError = "Ingrese un concepto válido y un monto mayor a 0.") }
            return
        }

        val montoFormateado = currencyFormat.format(monto) + " MXN"
        val nuevaDeduccion = Deduccion(concepto = concepto, montoRaw = monto, montoFormateado = montoFormateado)
        val updatedList = currentState.listaDeducciones + nuevaDeduccion

        _simulationUiState.update {
            it.copy(
                listaDeducciones = updatedList,
                nuevoConceptoInput = "",
                nuevoMontoInput = "",
                mensajeError = null
            )
        }
        calcularImpuestos()
    }

    fun eliminarDeduccion(id: String) {
        val updatedList = _simulationUiState.value.listaDeducciones.filter { it.id != id }
        _simulationUiState.update { it.copy(listaDeducciones = updatedList) }
        calcularImpuestos()
    }

    fun calcularImpuestos() {
        val state = _simulationUiState.value
        val bruto = state.ingresoBrutoInput.toDoubleOrNull()

        if (bruto == null || bruto < 0.0) {
            _simulationUiState.update { it.copy(mensajeError = "Ingrese un ingreso bruto válido.") }
            return
        }

        // Collection transformations in memory: sumOf deductions using montoRaw
        val totalDeducciones = state.listaDeducciones.sumOf { it.montoRaw }
        val ingresoGravable = (bruto - totalDeducciones).coerceAtLeast(0.0)

        // Find tariff bracket for ingresoGravable
        val tarifaMatch = tarifas.firstOrNull { ingresoGravable >= it.limiteInferior && ingresoGravable <= it.limiteSuperior }
            ?: tarifas.last()

        val excedente = ingresoGravable - tarifaMatch.limiteInferior
        val impuestoMarginal = excedente * (tarifaMatch.porcentajeSobreExcedente / 100.0)
        val isrTotal = (impuestoMarginal + tarifaMatch.cuotaFija).coerceAtLeast(0.0)

        val sueldoNeto = (bruto - isrTotal).coerceAtLeast(0.0)
        val porcentaje = if (bruto > 0.0) (isrTotal / bruto) * 100.0 else 0.0

        _simulationUiState.update {
            it.copy(
                ingresoBrutoFormateado = currencyFormat.format(bruto) + " MXN",
                ingresoGravableFormateado = currencyFormat.format(ingresoGravable) + " MXN",
                totalDeduccionesFormateado = currencyFormat.format(totalDeducciones) + " MXN",
                cuotaFijaFormateado = currencyFormat.format(tarifaMatch.cuotaFija) + " MXN",
                isrRetenidoFormateado = currencyFormat.format(isrTotal) + " MXN",
                sueldoNetoFormateado = currencyFormat.format(sueldoNeto) + " MXN",
                porcentajeRetencion = String.format(localeMx, "%.1f%%", porcentaje),
                mensajeError = null,
                isCalculated = true
            )
        }
    }

    fun guardarSimulacion() {
        val state = _simulationUiState.value
        val bruto = state.ingresoBrutoInput.toDoubleOrNull() ?: 0.0
        val totalDeducciones = state.listaDeducciones.sumOf { it.montoRaw }
        val ingresoGravable = (bruto - totalDeducciones).coerceAtLeast(0.0)
        val tarifaMatch = tarifas.firstOrNull { ingresoGravable >= it.limiteInferior && ingresoGravable <= it.limiteSuperior } ?: tarifas.last()
        val excedente = ingresoGravable - tarifaMatch.limiteInferior
        val isrTotal = (excedente * (tarifaMatch.porcentajeSobreExcedente / 100.0) + tarifaMatch.cuotaFija).coerceAtLeast(0.0)
        val sueldoNeto = (bruto - isrTotal).coerceAtLeast(0.0)
        val fecha = getCurrentDate()

        viewModelScope.launch {
            repository.saveSimulation(sueldoNeto, isrTotal, fecha)
        }
    }

    fun resetSimulation() {
        _simulationUiState.update { SimulationUiState() }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", localeMx)
        return sdf.format(Date())
    }
}
