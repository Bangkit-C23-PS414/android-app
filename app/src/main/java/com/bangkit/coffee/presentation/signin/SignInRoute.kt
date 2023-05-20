package com.bangkit.coffee.presentation.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignInRoute(
    coordinator: SignInCoordinator = rememberSignInCoordinator(),
    navigateUp: () -> Unit = {},
    navigateToForgotPassword: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(SignInState())

    // UI Actions
    val actions = rememberSignInActions(coordinator, navigateUp, navigateToForgotPassword)

    // UI Rendering
    SignInScreen(uiState, actions)
}


@Composable
fun rememberSignInActions(
    coordinator: SignInCoordinator,
    navigateUp: () -> Unit,
    navigateToForgotPassword: () -> Unit
): SignInActions {
    return remember(coordinator) {
        SignInActions(
            signIn = coordinator::signIn,
            setPasswordVisibility = coordinator::setPasswordVisibility,
            navigateUp = navigateUp,
            navigateToForgotPassword = navigateToForgotPassword
        )
    }
}