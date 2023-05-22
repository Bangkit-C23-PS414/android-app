package com.bangkit.coffee.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HistoryRoute(
    coordinator: HistoryCoordinator = rememberHistoryCoordinator(),
    navigateToDetail: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberHistoryActions(coordinator, navigateToDetail)

    // UI Rendering
    HistoryScreen(uiState, actions)
}


@Composable
fun rememberHistoryActions(
    coordinator: HistoryCoordinator,
    navigateToDetail: () -> Unit
): HistoryActions {
    return remember(coordinator) {
        HistoryActions(
            navigateToDetail = navigateToDetail
        )
    }
}