package com.dyland.exercise2.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.dyland.exercise2.main.MainUIEvents
import com.dyland.exercise2.main.MainUIState

@Composable
fun TabInstalledApps(
    mainUIState: MainUIState,
    navController: NavController,
    onEvent: (MainUIEvents) -> Unit
) {
    LazyApps(folders = mainUIState.folderApps, apps = mainUIState.installedApp, onEvent = onEvent)
}