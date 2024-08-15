package com.dyland.exercise2.folder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dyland.exercise2.components.AppRow
import com.dyland.exercise2.main.MainUIEvents
import com.dyland.exercise2.main.MainUIState
import com.dyland.exercise2.models.AppInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderView(
    name: String,
    mainUIState: MainUIState,
    navController: NavController,
    onEvent: (MainUIEvents) -> Unit
) {
    val apps = remember {
        mutableListOf<AppInfo>()
    }

    LaunchedEffect(mainUIState) {
        mainUIState.folderApps.forEach {
            if (it.name == name) {
                apps.clear()
                apps.addAll(it.apps)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    navController.popBackStack()
                                }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(text = name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                },
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(apps) { app ->
                    AppRow(
                        app,
                        onDeleteApp = {
                            onEvent(
                                MainUIEvents.OnRemoveAppInFolder(
                                    folderName = name,
                                    app = app,
                                )
                            )
                        },
                        onLongClick = {},
                        isShowDelete = true,
                    )
                }
            }
        }
    )
}
