package com.bangkit.coffee.presentation.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ResetPasswordRoute(
    coordinator: ResetPasswordCoordinator = rememberResetPasswordCoordinator(),
    navigateUp: () -> Unit = {},
    navigateToLogin: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(ResetPasswordState())

    // UI Actions
    val actions = rememberResetPasswordActions(coordinator, navigateUp, navigateToLogin)

    // UI Rendering
    ResetPasswordScreen(uiState, actions)
}


@Composable
fun rememberResetPasswordActions(
    coordinator: ResetPasswordCoordinator,
    navigateUp: () -> Unit,
    navigateToLogin: () -> Unit
): ResetPasswordActions {
    return remember(coordinator) {
        ResetPasswordActions(
            navigateUp = navigateUp,
            navigateToLogin = navigateToLogin
        )
    }
}