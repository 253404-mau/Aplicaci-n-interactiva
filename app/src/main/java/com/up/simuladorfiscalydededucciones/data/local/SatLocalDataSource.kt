package com.up.simuladorfiscalydededucciones.data.local

import android.content.Context
import com.up.simuladorfiscalydededucciones.R
import com.up.simuladorfiscalydededucciones.data.model.TarifaIsr
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

class SatLocalDataSource(private val context: Context) {
    fun getTarifas(): List<TarifaIsr> {
        val inputStream = context.resources.openRawResource(R.raw.tarifas_sat)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val jsonString = reader.use { it.readText() }

        val jsonArray = JSONArray(jsonString)
        val tarifas = mutableListOf<TarifaIsr>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            tarifas.add(
                TarifaIsr(
                    limiteInferior = obj.getDouble("limiteInferior"),
                    limiteSuperior = obj.getDouble("limiteSuperior"),
                    cuotaFija = obj.getDouble("cuotaFija"),
                    porcentajeSobreExcedente = obj.getDouble("porcentajeSobreExcedente")
                )
            )
        }
        return tarifas
    }
}
