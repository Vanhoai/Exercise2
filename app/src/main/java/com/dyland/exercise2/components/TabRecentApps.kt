package com.dyland.exercise2.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.dyland.exercise2.main.MainUIEvents
import com.dyland.exercise2.main.MainUIState
import com.dyland.exercise2.models.AppInfo

@Composable
fun TabRecentApps(
    mainUIState: MainUIState,
    navController: NavController,
    onEvent: (MainUIEvents) -> Unit
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.Top
    ) {
        makeRecentApps(mainUIState.recentApp, context)
    }
}

fun LazyListScope.makeRecentApps(apps: List<AppInfo>, context: Context) {
    items(apps) { app ->
        AppRow(
            app,
            onLongClick = {}
        )
    }
}