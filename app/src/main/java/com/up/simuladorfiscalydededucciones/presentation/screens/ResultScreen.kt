package com.up.simuladorfiscalydededucciones.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.up.simuladorfiscalydededucciones.presentation.components.FiscalProgressBar
import com.up.simuladorfiscalydededucciones.presentation.components.TaxBreakdownCard
import com.up.simuladorfiscalydededucciones.presentation.viewmodel.SimulationUiState
import com.up.simuladorfiscalydededucciones.ui.theme.SimuladorFiscalYDeDeduccionesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    uiState: SimulationUiState,
    onSaveSimulation: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Resultado de Simulación Fiscal") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Desglose Oficial SAT", style = MaterialTheme.typography.titleLarge)

            TaxBreakdownCard(
                ingresoBruto = uiState.ingresoBrutoFormateado,
                ingresoGravable = uiState.ingresoGravableFormateado,
                totalDeducciones = uiState.totalDeduccionesFormateado,
                cuotaFija = uiState.cuotaFijaFormateado,
                isrRetenido = uiState.isrRetenidoFormateado,
                sueldoNeto = uiState.sueldoNetoFormateado,
                porcentajeRetencion = uiState.porcentajeRetencion
            )

            FiscalProgressBar(
                progress = 0.16f,
                label = "Proporción ISR vs Ingreso Bruto"
            )

            Button(
                onClick = onSaveSimulation,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Save, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar Simulación en Historial")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    SimuladorFiscalYDeDeduccionesTheme {
        ResultScreen(
            uiState = SimulationUiState(
                ingresoBrutoFormateado = "$25,000.00 MXN",
                ingresoGravableFormateado = "$22,500.00 MXN",
                totalDeduccionesFormateado = "$2,500.00 MXN",
                cuotaFijaFormateado = "$1,182.88 MXN",
                isrRetenidoFormateado = "$3,250.00 MXN",
                sueldoNetoFormateado = "$21,750.00 MXN",
                porcentajeRetencion = "13.0%"
            ),
            onSaveSimulation = {},
            onBackClick = {}
        )
    }
}
