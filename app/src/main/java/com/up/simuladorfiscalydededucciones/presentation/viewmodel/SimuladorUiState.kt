package com.up.simuladorfiscalydededucciones.presentation.viewmodel

import com.up.simuladorfiscalydededucciones.data.model.Deduccion

data class SimulationUiState(
    val ingresoBrutoInput: String = "",
    val nuevoConceptoInput: String = "",
    val nuevoMontoInput: String = "",
    val listaDeducciones: List<Deduccion> = emptyList(),
    val ingresoBrutoFormateado: String = "$0.00 MXN",
    val ingresoGravableFormateado: String = "$0.00 MXN",
    val totalDeduccionesFormateado: String = "$0.00 MXN",
    val cuotaFijaFormateado: String = "$0.00 MXN",
    val isrRetenidoFormateado: String = "$0.00 MXN",
    val sueldoNetoFormateado: String = "$0.00 MXN",
    val porcentajeRetencion: String = "0.0%",
    val mensajeError: String? = null,
    val isCalculated: Boolean = false
)
