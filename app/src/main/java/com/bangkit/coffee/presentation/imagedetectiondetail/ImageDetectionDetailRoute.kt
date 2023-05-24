package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ImageDetectionDetailRoute(
    coordinator: ImageDetectionDetailCoordinator = rememberImageDetectionDetailCoordinator(),
    navigateUp: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberImageDetectionDetailActions(coordinator, navigateUp)

    // UI Rendering
    ImageDetectionDetailScreen(uiState, actions)
}


@Composable
fun rememberImageDetectionDetailActions(
    coordinator: ImageDetectionDetailCoordinator,
    navigateUp: () -> Unit,
): ImageDetectionDetailActions {
    return remember(coordinator) {
        ImageDetectionDetailActions(
            navigateUp = navigateUp,
            refresh = coordinator::refresh
        )
    }
}