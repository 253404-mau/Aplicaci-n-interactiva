package com.up.simuladorfiscalydededucciones.presentation.viewmodel

import com.up.simuladorfiscalydededucciones.data.model.SimulationHistoryItem

data class DashboardUiState(
    val ultimoSueldoNetoFormateado: String = "$0.00 MXN",
    val ultimoIsrFormateado: String = "$0.00 MXN",
    val ultimaFecha: String = "",
    val historialSimulaciones: List<SimulationHistoryItem> = emptyList(),
    val isLoading: Boolean = false
)
