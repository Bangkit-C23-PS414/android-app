package com.bangkit.coffee.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.domain.entity.ImageDetection


/**
 * UI State that represents HistoryScreen
 **/
data class HistoryState(
    val imageDetections: List<ImageDetection> = emptyList()
)

/**
 * History Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class HistoryActions(
    val navigateToDetail: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalHistoryActions = staticCompositionLocalOf<HistoryActions> {
    error("{NAME} Actions Were not provided, make sure ProvideHistoryActions is called")
}

@Composable
fun ProvideHistoryActions(actions: HistoryActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalHistoryActions provides actions) {
        content.invoke()
    }
}

