package com.up.simuladorfiscalydededucciones.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TaxBreakdownCard(
    ingresoBruto: String,
    ingresoGravable: String,
    totalDeducciones: String,
    cuotaFija: String,
    isrRetenido: String,
    sueldoNeto: String,
    porcentajeRetencion: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Desglose Fiscal SAT", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(12.dp))
            BreakdownRow(label = "Ingreso Bruto:", value = ingresoBruto)
            BreakdownRow(label = "Total Deducciones:", value = totalDeducciones)
            BreakdownRow(label = "Ingreso Gravable:", value = ingresoGravable)
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            BreakdownRow(label = "Cuota Fija:", value = cuotaFija)
            BreakdownRow(label = "ISR Retenido:", value = isrRetenido, isHighlight = true)
            BreakdownRow(label = "Tasa Efectiva ISR:", value = porcentajeRetencion)
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            BreakdownRow(label = "Sueldo Neto Estimado:", value = sueldoNeto, isNet = true)
        }
    }
}

@Composable
private fun BreakdownRow(label: String, value: String, isHighlight: Boolean = false, isNet: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (isNet) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            color = if (isNet) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = if (isNet) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            color = when {
                isNet -> MaterialTheme.colorScheme.primary
                isHighlight -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onSurface
            }
        )
    }
}
