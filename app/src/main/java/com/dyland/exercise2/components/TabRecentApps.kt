package com.dyland.exercise2.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.dyland.exercise2.main.MainUIEvents
import com.dyland.exercise2.main.MainUIState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TabRecentApps(
    mainUIState: MainUIState,
    navController: NavController,
    onEvent: (MainUIEvents) -> Unit
) {
    LazyApps(folders = mainUIState.folderApps, apps = mainUIState.recentApp, onEvent = onEvent)
}
