package com.dyland.exercise2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dyland.exercise2.main.MainViewModel
import com.dyland.exercise2.ui.theme.Exercise2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val hasPermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.PACKAGE_USAGE_STATS
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasPermission) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            this.startActivity(intent)
        }

        setContent {
            Exercise2Theme {
                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainUIState = mainViewModel.mainUiState.collectAsState().value
                
                LaunchedEffect(Unit) {
                    mainViewModel.initialize(applicationContext)
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    MainNavigation(mainUIState = mainUIState, onEvent = mainViewModel::onEvent)
                }
            }
        }
    }
}
