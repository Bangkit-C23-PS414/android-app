package com.bangkit.coffee.presentation.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WelcomeRoute(
    coordinator: WelcomeCoordinator = rememberWelcomeCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(WelcomeState())

    // UI Actions
    val actions = rememberWelcomeActions(coordinator)

    // UI Rendering
    WelcomeScreen(uiState, actions)
}


@Composable
fun rememberWelcomeActions(coordinator: WelcomeCoordinator): WelcomeActions {
    return remember(coordinator) {
        WelcomeActions(
            onClick = coordinator::doStuff
        )
    }
}