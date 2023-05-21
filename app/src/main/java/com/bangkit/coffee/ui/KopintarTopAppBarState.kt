package com.bangkit.coffee.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class KopintarTopAppBarState(
    val title: String = "",
    val actions: (@Composable RowScope.() -> Unit)? = null
)