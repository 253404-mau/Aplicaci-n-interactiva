package com.up.simuladorfiscalydededucciones.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.up.simuladorfiscalydededucciones.data.model.SimulationHistoryItem
import com.up.simuladorfiscalydededucciones.presentation.components.FiscalProgressBar
import com.up.simuladorfiscalydededucciones.presentation.components.HistoryTile
import com.up.simuladorfiscalydededucciones.presentation.components.StatCard
import com.up.simuladorfiscalydededucciones.presentation.viewmodel.DashboardUiState
import com.up.simuladorfiscalydededucciones.ui.theme.SimuladorFiscalYDeDeduccionesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onNavigateToSimulation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Simulador Fiscal MX") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToSimulation,
                icon = { Icon(Icons.Default.Add, contentDescription = "Nueva simulación") },
                text = { Text("Simular ISR") }
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
                Text(text = "Resumen Última Simulación", style = MaterialTheme.typography.titleLarge)
            }
            item {
                StatCard(
                    title = "Último Sueldo Neto",
                    value = uiState.ultimoSueldoNetoFormateado,
                    icon = Icons.Default.AttachMoney
                )
            }
            item {
                StatCard(
                    title = "Último ISR Retenido",
                    value = uiState.ultimoIsrFormateado,
                    icon = Icons.Default.MoneyOff,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            item {
                FiscalProgressBar(progress = 0.15f, label = "Tasa Estimada Última Simulación (15%)")
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Historial Reciente", style = MaterialTheme.typography.titleMedium)
            }
            if (uiState.historialSimulaciones.isEmpty()) {
                item {
                    Text(text = "No hay simulaciones guardadas aún.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                items(uiState.historialSimulaciones) { item ->
                    HistoryTile(
                        sueldoNeto = "$${item.sueldoNeto} MXN",
                        isrRetenido = "$${item.isrRetenido} MXN",
                        fecha = item.fecha
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    SimuladorFiscalYDeDeduccionesTheme {
        DashboardScreen(
            uiState = DashboardUiState(
                ultimoSueldoNetoFormateado = "$22,500.00 MXN",
                ultimoIsrFormateado = "$3,500.00 MXN",
                historialSimulaciones = listOf(
                    SimulationHistoryItem("1", 22500.0, 3500.0, "15/10/2026 11:00")
                )
            ),
            onNavigateToSimulation = {}
        )
    }
}
