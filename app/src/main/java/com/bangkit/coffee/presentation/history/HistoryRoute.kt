package com.bangkit.coffee.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun HistoryRoute(
    coordinator: HistoryCoordinator = rememberHistoryCoordinator(),
    navigateToDetailImageDetection: (String) -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()
    val pagerState = coordinator.pagerFlow.collectAsLazyPagingItems()

    // UI Actions
    val actions = rememberHistoryActions(coordinator, navigateToDetailImageDetection)

    // UI Rendering
    HistoryScreen(uiState, actions, pagerState)
}


@Composable
fun rememberHistoryActions(
    coordinator: HistoryCoordinator,
    navigateToDetailImageDetection: (String) -> Unit
): HistoryActions {
    return remember(coordinator) {
        HistoryActions(
            navigateToDetailImageDetection = navigateToDetailImageDetection,
            toggleFilter = coordinator::toggleFilter,
            applyFilter = coordinator::applyFilter,
            resetFilter = coordinator::resetFilter
        )
    }
}