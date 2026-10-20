package com.up.simuladorfiscalydededucciones.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.up.simuladorfiscalydededucciones.data.model.Deduccion
import com.up.simuladorfiscalydededucciones.presentation.components.CurrencyTextField
import com.up.simuladorfiscalydededucciones.presentation.components.DeductionItemRow
import com.up.simuladorfiscalydededucciones.presentation.viewmodel.SimulationUiState
import com.up.simuladorfiscalydededucciones.ui.theme.SimuladorFiscalYDeDeduccionesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulationScreen(
    uiState: SimulationUiState,
    onIngresoBrutoChanged: (String) -> Unit,
    onNuevoConceptoChanged: (String) -> Unit,
    onNuevoMontoChanged: (String) -> Unit,
    onAgregarDeduccion: () -> Unit,
    onEliminarDeduccion: (String) -> Unit,
    onCalcularClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Simulador de Ingresos y Deducciones") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = "1. Ingrese su sueldo bruto mensual", style = MaterialTheme.typography.titleMedium)
            }
            item {
                CurrencyTextField(
                    value = uiState.ingresoBrutoInput,
                    onValueChange = onIngresoBrutoChanged,
                    label = "Ingreso Bruto Mensual ($)",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "2. Agregue deducciones personales (opcional)", style = MaterialTheme.typography.titleMedium)
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.nuevoConceptoInput,
                        onValueChange = onNuevoConceptoChanged,
                        label = { Text("Concepto (ej. Gastos Médicos)") },
                        modifier = Modifier.weight(1.5f),
                        singleLine = true
                    )
                    CurrencyTextField(
                        value = uiState.nuevoMontoInput,
                        onValueChange = onNuevoMontoChanged,
                        label = "Monto ($)",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            item {
                Button(
                    onClick = onAgregarDeduccion,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar Deducción")
                }
            }

            if (uiState.mensajeError != null) {
                item {
                    Text(
                        text = uiState.mensajeError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (uiState.listaDeducciones.isNotEmpty()) {
                item {
                    Text(text = "Deducciones Registradas", style = MaterialTheme.typography.titleSmall)
                }
                items(uiState.listaDeducciones) { deduccion ->
                    DeductionItemRow(
                        concepto = deduccion.concepto,
                        montoFormateado = deduccion.montoFormateado,
                        onDeleteClick = { onEliminarDeduccion(deduccion.id) }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onCalcularClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Calculate, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Calcular Impuestos y Ver Resultado")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SimulationScreenPreview() {
    SimuladorFiscalYDeDeduccionesTheme {
        SimulationScreen(
            uiState = SimulationUiState(
                ingresoBrutoInput = "25000",
                listaDeducciones = listOf(
                    Deduccion(id = "1", concepto = "Gastos Médicos", montoRaw = 2500.0, montoFormateado = "$2,500.00 MXN")
                )
            ),
            onIngresoBrutoChanged = {},
            onNuevoConceptoChanged = {},
            onNuevoMontoChanged = {},
            onAgregarDeduccion = {},
            onEliminarDeduccion = {},
            onCalcularClick = {},
            onBackClick = {}
        )
    }
}
