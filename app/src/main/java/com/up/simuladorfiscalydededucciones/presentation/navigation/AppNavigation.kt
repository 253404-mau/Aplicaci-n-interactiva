package com.up.simuladorfiscalydededucciones.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.up.simuladorfiscalydededucciones.presentation.screens.DashboardScreen
import com.up.simuladorfiscalydededucciones.presentation.screens.DeductibleGuideScreen
import com.up.simuladorfiscalydededucciones.presentation.screens.ResultScreen
import com.up.simuladorfiscalydededucciones.presentation.screens.SimulationScreen
import com.up.simuladorfiscalydededucciones.presentation.viewmodel.FiscalViewModel

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object Simulation : Screen("simulation")
    data object Result : Screen("result")
    data object Guide : Screen("guide")
}

@Composable
fun AppNavigation(viewModel: FiscalViewModel = viewModel()) {
    val navController = rememberNavController()
    val dashboardState by viewModel.dashboardUiState.collectAsState()
    val simulationState by viewModel.simulationUiState.collectAsState()

    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                uiState = dashboardState,
                onNavigateToSimulation = {
                    viewModel.resetSimulation()
                    navController.navigate(Screen.Simulation.route)
                },
                onNavigateToGuide = {
                    navController.navigate(Screen.Guide.route)
                }
            )
        }
        composable(Screen.Simulation.route) {
            SimulationScreen(
                uiState = simulationState,
                onIngresoBrutoChanged = viewModel::onIngresoBrutoChanged,
                onNuevoConceptoChanged = viewModel::onNuevoConceptoChanged,
                onNuevoMontoChanged = viewModel::onNuevoMontoChanged,
                onAgregarDeduccion = viewModel::agregarDeduccion,
                onEliminarDeduccion = viewModel::eliminarDeduccion,
                onCalcularClick = {
                    viewModel.calcularImpuestos()
                    navController.navigate(Screen.Result.route)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Result.route) {
            ResultScreen(
                uiState = simulationState,
                onSaveSimulation = {
                    viewModel.guardarSimulacion()
                    navController.popBackStack(Screen.Dashboard.route, inclusive = false)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Guide.route) {
            DeductibleGuideScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
