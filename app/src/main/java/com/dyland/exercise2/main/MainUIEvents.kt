package com.dyland.exercise2.main

import com.dyland.exercise2.models.AppInfo

sealed class MainUIEvents {
    data class OnDeleteFolder(val name: String) : MainUIEvents()
    data class OnAddNewFolder(val name: String) : MainUIEvents()
    data class OnUpdateFolder(val oldName: String, val newName: String) : MainUIEvents()
    data class OnAddAppToFolder(val folderName: String, val app: AppInfo) : MainUIEvents()
    data class OnRemoveAppInFolder(val folderName: String, val app: AppInfo) : MainUIEvents()
}