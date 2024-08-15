package com.dyland.exercise2

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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dyland.exercise2.main.MainViewModel
import com.dyland.exercise2.ui.theme.Exercise2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val permission = "android.permission.PACKAGE_USAGE_STATS"
        val granted = packageManager.checkPermission(
            permission,
            packageName
        ) == PackageManager.PERMISSION_GRANTED

//        val hasPermission = ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.PACKAGE_USAGE_STATS
//        ) == PackageManager.PERMISSION_GRANTED

        // FIX ME !! (after allow permission, restart app then app again go to setting)
        if (!granted) {
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
