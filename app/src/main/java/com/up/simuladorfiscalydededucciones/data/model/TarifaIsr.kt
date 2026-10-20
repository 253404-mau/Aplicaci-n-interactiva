package com.up.simuladorfiscalydededucciones.data.model

data class TarifaIsr(
    val limiteInferior: Double,
    val limiteSuperior: Double,
    val cuotaFija: Double,
    val porcentajeSobreExcedente: Double
)
