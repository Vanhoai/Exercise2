package com.dyland.exercise2.models

import androidx.compose.runtime.Composable

data class TabItem (
    val text: String,
    val screen: @Composable ()->Unit
)