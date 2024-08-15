package com.dyland.exercise2.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.dyland.exercise2.models.AppInfo

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun AppRow(
    app: AppInfo,
    onClick: (() -> Unit)? = null,
    onLongClick: () -> Unit,
    isShowDelete: Boolean = false,
    onDeleteApp: (() -> Unit)? = null
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    if (onClick != null) {
                        onClick()
                    } else {
                        val launchIntent =
                            context.packageManager.getLaunchIntentForPackage(app.packageName)
                        if (launchIntent != null) {
                            Log.d("Exercise2", "Launching ${app.label} (${app.packageName})")
                            context.startActivity(launchIntent)
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "Can not launch ${app.label} (${app.packageName})",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
                },
                onLongClick = { onLongClick() }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isShowDelete) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (onDeleteApp != null) {
                            onDeleteApp()
                        }
                    }
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            bitmap = app.icon.toBitmap().asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = app.label, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = app.packageName, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}