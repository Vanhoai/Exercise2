package com.dyland.exercise2

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dyland.exercise2.components.AppRow
import com.dyland.exercise2.main.MainViewModel
import com.dyland.exercise2.models.AppInfo
import com.dyland.exercise2.ui.theme.Exercise2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Exercise2Theme {
                val mainViewModel = hiltViewModel<MainViewModel>()
                val mainUIState = mainViewModel.mainUiState.collectAsState().value

                LaunchedEffect(Unit) {
                    mainViewModel.initialize(applicationContext)
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    MainNavigation(mainUIState = mainUIState,onEvent = mainViewModel::onEvent)
                }
            }
        }
    }
}
