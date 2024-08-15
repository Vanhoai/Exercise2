package com.dyland.exercise2.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dyland.exercise2.R
import com.dyland.exercise2.Routes
import com.dyland.exercise2.main.MainUIEvents
import com.dyland.exercise2.main.MainUIState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FoldersView (
    mainUIState: MainUIState,
    navController: NavController,
    onEvent: (MainUIEvents) -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val valueTextField = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val isShowBottomSheet = remember {
        mutableStateOf(false)
    }
    val isAddNewFolder = remember {
        mutableStateOf(false)
    }
    val folderSelected = remember {
        mutableStateOf("")
    }

    Log.d("SizeFolder", mainUIState.folderApps.size.toString())

    Column {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Manager Folders",
            )

            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        isAddNewFolder.value = true
                        isShowBottomSheet.value = true
                        valueTextField.value = "New Folder"
                    }
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
        ) {
            mainUIState.folderApps.forEach { folder ->
                Column (
                    modifier = Modifier
                        .width(80.dp)
                        .combinedClickable(
                            onClick = {
                                Log.d("Exercise2", "Click on Custom Folder")
                                navController.navigate(
                                    Routes.FOLDER_SCREEN + "/${folder.name}"
                                )
                            },
                            onLongClick = {
                                Log.d("Exercise2", "Long click on Custom Folder")
                                isShowBottomSheet.value = true
                                valueTextField.value = folder.name
                                folderSelected.value = folder.name
                            }
                        ),
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
    }

    if (isShowBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                isShowBottomSheet.value = false
            },
            sheetState = sheetState
        ) {
            Text(text = "Rename Folder", modifier = Modifier.padding(horizontal = 16.dp))
            TextField(
                value = valueTextField.value,
                onValueChange = {
                    valueTextField.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (!isAddNewFolder.value) {
                    Button(
                        onClick = {
                            scope.launch {
                                onEvent(MainUIEvents.OnDeleteFolder(folderSelected.value))
                                sheetState.hide()
                                isShowBottomSheet.value = false
                                valueTextField.value = ""
                                isAddNewFolder.value = false
                                folderSelected.value = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        )
                    ) {
                        Text(text = "Delete Folder", color = Color.White)
                    }
                }

                Button(onClick = {
                    if (isAddNewFolder.value) {
                        if (valueTextField.value == "") {
                            val toast = Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT)
                            toast.show()
                            return@Button
                        }
                        Log.d("Exercise2", "Add Folder")
                        scope.launch {
                            onEvent(MainUIEvents.OnAddNewFolder(valueTextField.value))
                            sheetState.hide()
                            isShowBottomSheet.value = false
                            valueTextField.value = ""
                            isAddNewFolder.value = false
                        }
                    } else {
                        if (valueTextField.value == "") {
                            val toast = Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT)
                            toast.show()
                            return@Button
                        }
                        Log.d("Exercise2", "Update Folder")
                        scope.launch {
                            onEvent(MainUIEvents.OnUpdateFolder(oldName = folderSelected.value, newName = valueTextField.value))
                            sheetState.hide()
                            isShowBottomSheet.value = false
                            valueTextField.value = ""
                        }
                    }
                }) {
                    Text(text = if (isAddNewFolder.value) { "Add Folder" } else { "Save Folder" })
                }
            }
        }
    }
}