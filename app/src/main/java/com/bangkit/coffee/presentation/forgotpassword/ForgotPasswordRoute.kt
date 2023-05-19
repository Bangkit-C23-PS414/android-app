package com.bangkit.coffee.presentation.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ForgotPasswordRoute(
    coordinator: ForgotPasswordCoordinator = rememberForgotPasswordCoordinator(),
    navigateUp: () -> Unit,
    navigateToVerifyOTP: () -> Unit
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(ForgotPasswordState())

    // UI Actions
    val actions = rememberForgotPasswordActions(coordinator, navigateUp, navigateToVerifyOTP)

    // UI Rendering
    ForgotPasswordScreen(uiState, actions)
}


@Composable
fun rememberForgotPasswordActions(
    coordinator: ForgotPasswordCoordinator,
    navigateUp: () -> Unit,
    navigateToVerifyOTP: () -> Unit
): ForgotPasswordActions {
    return remember(coordinator) {
        ForgotPasswordActions(
            forgotPassword = coordinator::forgotPassword,
            navigateUp = navigateUp,
            navigateToVerifyOTP = navigateToVerifyOTP
        )
    }
}