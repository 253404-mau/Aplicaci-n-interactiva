package com.up.simuladorfiscalydededucciones.data.repository

import android.content.Context
import com.up.simuladorfiscalydededucciones.data.datastore.UserPreferencesRepository
import com.up.simuladorfiscalydededucciones.data.local.SatLocalDataSource
import com.up.simuladorfiscalydededucciones.data.model.TarifaIsr
import kotlinx.coroutines.flow.Flow

class FiscalRepository(context: Context) {
    private val localDataSource = SatLocalDataSource(context)
    private val preferencesRepository = UserPreferencesRepository(context)

    fun getTarifasIsr(): List<TarifaIsr> {
        return localDataSource.getTarifas()
    }

    val lastSimulationFlow: Flow<Triple<Double, Double, String>> = preferencesRepository.lastSimulationFlow

    suspend fun saveSimulation(netSalary: Double, isr: Double, date: String) {
        preferencesRepository.saveLastSimulation(netSalary, isr, date)
    }
}
