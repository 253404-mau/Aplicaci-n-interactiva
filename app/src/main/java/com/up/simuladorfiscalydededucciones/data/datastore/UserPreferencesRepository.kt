package com.up.simuladorfiscalydededucciones.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fiscal_preferences")

class UserPreferencesRepository(private val context: Context) {
    private object PreferencesKeys {
        val LAST_NET_SALARY = doublePreferencesKey("last_net_salary")
        val LAST_ISR = doublePreferencesKey("last_isr")
        val LAST_DATE = stringPreferencesKey("last_date")
    }

    val lastSimulationFlow: Flow<Triple<Double, Double, String>> = context.dataStore.data
        .map { preferences ->
            val netSalary = preferences[PreferencesKeys.LAST_NET_SALARY] ?: 0.0
            val isr = preferences[PreferencesKeys.LAST_ISR] ?: 0.0
            val date = preferences[PreferencesKeys.LAST_DATE] ?: ""
            Triple(netSalary, isr, date)
        }

    suspend fun saveLastSimulation(netSalary: Double, isr: Double, date: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_NET_SALARY] = netSalary
            preferences[PreferencesKeys.LAST_ISR] = isr
            preferences[PreferencesKeys.LAST_DATE] = date
        }
    }
}
