package com.up.simuladorfiscalydededucciones.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.up.simuladorfiscalydededucciones.ui.theme.SimuladorFiscalYDeDeduccionesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeductibleGuideScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Guía de Deducciones Art. 151 LISR") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "¿Qué son las Deducciones Personales?",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Son gastos que como contribuyente tienes derecho a restar de tus ingresos acumulables en tu declaración anual. Esto disminuye tu base gravable, resultando en un menor ISR a pagar o un saldo a favor mayor.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Gastos SÍ Deducibles (Art. 151 LISR)",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        DeductionGuideItem(
                            title = "Salud",
                            description = "Honorarios médicos, dentales, psicólogos, nutriólogos, gastos hospitalarios y análisis clínicos (para ti, cónyuge, hijos y papás si no perciben ingresos)."
                        )
                        DeductionGuideItem(
                            title = "Educación",
                            description = "Colegiaturas desde preescolar hasta bachillerato (con límites anuales por nivel) y transporte escolar obligatorio."
                        )
                        DeductionGuideItem(
                            title = "Vivienda y Otros",
                            description = "Intereses reales efectivamente pagados por créditos hipotecarios (INFONAVIT, FOVISSSTE o bancarios), donativos autorizados y aportaciones complementarias de retiro (AFORE)."
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Requisito indispensable: Pagar con tarjeta de crédito/débito, transferencia o cheque nominativo y contar con factura electrónica (CFDI) con uso de CFDI correcto.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Gastos NO Deducibles / Errores Comunes",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        DeductionGuideItem(
                            title = "Medicamentos en Farmacia",
                            description = "Las compras directas de medicamentos en farmacias no son deducibles por sí solas (solo si vienen incluidas en la factura del hospital).",
                            textColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                        DeductionGuideItem(
                            title = "Colegiaturas Universitarias",
                            description = "Los pagos de educación superior (universidad o posgrado) no entran en las deducciones personales del Art. 151.",
                            textColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                        DeductionGuideItem(
                            title = "Pagos en Efectivo",
                            description = "Cualquier gasto pagado en efectivo pierde automáticamente su deducibilidad ante el SAT.",
                            textColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeductionGuideItem(title: String, description: String, textColor: Color = MaterialTheme.colorScheme.onSurface) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = "• $title", style = MaterialTheme.typography.bodyLarge, color = textColor)
        Text(text = description, style = MaterialTheme.typography.bodyMedium, color = textColor.copy(alpha = 0.8f))
    }
}

@Preview(showBackground = true)
@Composable
fun DeductibleGuideScreenPreview() {
    SimuladorFiscalYDeDeduccionesTheme {
        DeductibleGuideScreen(onNavigateBack = {})
    }
}
