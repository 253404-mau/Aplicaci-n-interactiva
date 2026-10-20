package com.up.simuladorfiscalydededucciones.data.model

import java.util.UUID

data class Deduccion(
    val id: String = UUID.randomUUID().toString(),
    val concepto: String,
    val montoRaw: Double,
    val montoFormateado: String
)
