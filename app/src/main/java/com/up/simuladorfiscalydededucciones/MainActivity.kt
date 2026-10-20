package com.up.simuladorfiscalydededucciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.up.simuladorfiscalydededucciones.presentation.navigation.AppNavigation
import com.up.simuladorfiscalydededucciones.presentation.viewmodel.FiscalViewModel
import com.up.simuladorfiscalydededucciones.ui.theme.FiscalAppTheme

class MainActivity : ComponentActivity() {
    private val viewModel: FiscalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FiscalAppTheme {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
