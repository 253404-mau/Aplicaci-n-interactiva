package com.up.simuladorfiscalydededucciones.data.model

data class SimulationHistoryItem(
    val id: String,
    val sueldoNeto: Double,
    val isrRetenido: Double,
    val fecha: String
)
