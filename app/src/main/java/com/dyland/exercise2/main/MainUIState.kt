package com.dyland.exercise2.main

import com.dyland.exercise2.models.AppInfo
import com.dyland.exercise2.models.FolderItem

data class MainUIState (
    val installedApp: List<AppInfo> = emptyList(),
    val installedAppShow: List<AppInfo> = emptyList(),
    val recentApp: List<AppInfo> = emptyList(),
    val folderApps: List<FolderItem> = listOf(
        FolderItem(
            name = "Custom",
            apps = emptyList()
        )
    )
)