package com.bangkit.coffee.presentation.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WelcomeRoute(
    coordinator: WelcomeCoordinator = rememberWelcomeCoordinator(),
    navigateToSignIn: () -> Unit = {},
    navigateToSignUp: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberWelcomeActions(coordinator, navigateToSignIn, navigateToSignUp)

    // UI Rendering
    WelcomeScreen(uiState, actions)
}


@Composable
fun rememberWelcomeActions(
    coordinator: WelcomeCoordinator,
    navigateToSignIn: () -> Unit,
    navigateToSignUp: () -> Unit
): WelcomeActions {
    return remember(coordinator) {
        WelcomeActions(
            navigateToSignIn = navigateToSignIn,
            navigateToSignUp = navigateToSignUp
        )
    }
}