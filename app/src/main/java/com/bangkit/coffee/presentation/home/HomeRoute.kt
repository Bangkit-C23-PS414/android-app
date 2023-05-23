package com.bangkit.coffee.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    coordinator: HomeCoordinator = rememberHomeCoordinator(),
    navigateToCamera: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberHomeActions(coordinator, navigateToCamera)

    // UI Rendering
    HomeScreen(uiState, actions)
}


@Composable
fun rememberHomeActions(
    coordinator: HomeCoordinator,
    navigateToCamera: () -> Unit
): HomeActions {
    return remember(coordinator) {
        HomeActions(
            navigateToCamera = navigateToCamera
        )
    }
}