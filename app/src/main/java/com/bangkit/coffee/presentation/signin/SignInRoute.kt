package com.bangkit.coffee.presentation.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignInRoute(
    coordinator: SignInCoordinator = rememberSignInCoordinator(),
    navigateToForgotPassword: () -> Unit = {},
    navigateToDashboard: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberSignInActions(
        coordinator,
        navigateToForgotPassword,
        navigateToDashboard
    )

    // UI Rendering
    SignInScreen(uiState, actions)
}


@Composable
fun rememberSignInActions(
    coordinator: SignInCoordinator,
    navigateToForgotPassword: () -> Unit,
    navigateToDashboard: () -> Unit
): SignInActions {
    return remember(coordinator) {
        SignInActions(
            signIn = coordinator::signIn,
            setPasswordVisibility = coordinator::setPasswordVisibility,
            navigateToForgotPassword = navigateToForgotPassword,
            navigateToDashboard = navigateToDashboard
        )
    }
}