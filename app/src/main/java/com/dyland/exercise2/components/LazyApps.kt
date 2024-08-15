package com.dyland.exercise2.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dyland.exercise2.R
import com.dyland.exercise2.main.MainUIEvents
import com.dyland.exercise2.models.AppInfo
import com.dyland.exercise2.models.FolderItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LazyApps(
    folders: List<FolderItem>,
    apps: List<AppInfo>,
    onEvent: (MainUIEvents) -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val isShowBottomSheet = remember {
        mutableStateOf(false)
    }
    val folderSelected = remember {
        mutableStateOf("")
    }
    val appSelected = remember {
        mutableStateOf<AppInfo?>(null)
    }

    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(apps) { app ->
            AppRow(
                app,
                onLongClick = {
                    isShowBottomSheet.value = true
                    appSelected.value = app
                }
            )
        }
    }

    if (isShowBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                isShowBottomSheet.value = false
                folderSelected.value = ""
                appSelected.value = null
            },
            sheetState = sheetState
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Add App to Folder")
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    folders.forEach { folder ->
                        Column(
                            modifier = Modifier
                                .width(80.dp)
                                .background(
                                    color = if (folder.name == folderSelected.value) {
                                        androidx.compose.material3.MaterialTheme.colorScheme.primary
                                    } else {
                                        androidx.compose.material3.MaterialTheme.colorScheme.surface
                                    }
                                )
                                .clickable {
                                    if (folder.name == folderSelected.value) {
                                        folderSelected.value = ""
                                    } else {
                                        folderSelected.value = folder.name
                                    }
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_folder),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(40.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = folder.name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }


                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        if (folderSelected.value == "") {
                            Toast.makeText(context, "Please select a folder", Toast.LENGTH_SHORT)
                                .show()
                            return@Button
                        }

                        scope.launch {
                            onEvent(
                                MainUIEvents.OnAddAppToFolder(
                                    folderSelected.value,
                                    appSelected.value!!
                                )
                            )
                            sheetState.hide()
                            isShowBottomSheet.value = false
                            folderSelected.value = ""
                        }
                    }
                ) {
                    Text(text = "Add")
                }
            }
        }
    }
}