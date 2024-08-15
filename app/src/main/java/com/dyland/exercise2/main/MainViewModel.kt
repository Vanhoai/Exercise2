package com.dyland.exercise2.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyland.exercise2.Utils
import com.dyland.exercise2.models.FolderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private var _mainUiState = MutableStateFlow(MainUIState())
    var mainUiState = _mainUiState.asStateFlow()

    fun initialize(context: Context) {
        viewModelScope.launch {
            val installedApps = Utils.getInstalledApps(context.packageManager)
            val recentApps = Utils.getRecentApps(context)

            val folderCustom = FolderItem(
                name = "Custom",
                apps = installedApps.subList(0, 5)
            )

            _mainUiState.update {
                it.copy(
                    installedApp = installedApps,
                    recentApp = recentApps,
                    installedAppShow = installedApps.subList(0, 30),
                    folderApps = listOf(folderCustom)
                )
            }
        }
    }

    fun onEvent(event: MainUIEvents) {
        when (event) {
            is MainUIEvents.OnDeleteFolder -> {
                viewModelScope.launch {
                    _mainUiState.update { it ->
                        it.copy(
                            folderApps = it.folderApps.filter {
                                it.name != event.name
                            }
                        )
                    }
                }
            }

            is MainUIEvents.OnAddNewFolder -> {
                viewModelScope.launch {
                    _mainUiState.update { it ->
                        it.copy(
                            folderApps = it.folderApps + FolderItem(name = event.name, emptyList())
                        )
                    }
                }
            }

            is MainUIEvents.OnUpdateFolder -> {
                viewModelScope.launch {
                    _mainUiState.update { it ->
                        it.copy(
                            folderApps = it.folderApps.map { folder ->
                                if (folder.name == event.oldName) {
                                    folder.copy(name = event.newName)
                                } else {
                                    folder
                                }
                            }
                        )
                    }
                }
            }

            is MainUIEvents.OnAddAppToFolder -> {
                viewModelScope.launch {
                    _mainUiState.update { it ->
                        it.copy(
                            folderApps = it.folderApps.map { folder ->
                                if (folder.name == event.folderName) {
                                    val checkExist =
                                        folder.apps.find { it.packageName == event.app.packageName } != null
                                    if (checkExist) {
                                        folder
                                    } else {
                                        folder.copy(apps = folder.apps + event.app)
                                    }
                                } else {
                                    folder
                                }
                            }
                        )
                    }
                }
            }

            is MainUIEvents.OnRemoveAppInFolder -> {
                viewModelScope.launch {
                    _mainUiState.update { it ->
                        it.copy(
                            folderApps = it.folderApps.map { folder ->
                                if (folder.name == event.folderName) {
                                    Log.d("Exercise2", "Folder: ${folder.apps}")
                                    val newApps = folder.apps.filter {
                                        Log.d("Exercise2", "App: ${it.packageName}")
                                        it.packageName != event.app.packageName
                                    }

                                    Log.d("Exercise2", "New AppSize: ${newApps.size}")

                                    folder.copy(apps = newApps)
                                } else {
                                    folder
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}