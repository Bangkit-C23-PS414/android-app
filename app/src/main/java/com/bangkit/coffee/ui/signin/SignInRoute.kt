package com.bangkit.coffee.ui.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignInRoute(
    coordinator: SignInCoordinator = rememberSignInCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(SignInState())

    // UI Actions
    val actions = rememberSignInActions(coordinator)

    // UI Rendering
    SignInScreen(uiState, actions)
}


@Composable
fun rememberSignInActions(coordinator: SignInCoordinator): SignInActions {
    return remember(coordinator) {
        SignInActions(
            onClick = coordinator::doStuff
        )
    }
}