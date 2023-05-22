package com.bangkit.coffee.presentation.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ForgotPasswordRoute(
    coordinator: ForgotPasswordCoordinator = rememberForgotPasswordCoordinator(),
    navigateToVerifyOTP: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberForgotPasswordActions(coordinator, navigateToVerifyOTP)

    // UI Rendering
    ForgotPasswordScreen(uiState, actions)
}


@Composable
fun rememberForgotPasswordActions(
    coordinator: ForgotPasswordCoordinator,
    navigateToVerifyOTP: () -> Unit
): ForgotPasswordActions {
    return remember(coordinator) {
        ForgotPasswordActions(
            forgotPassword = coordinator::forgotPassword,
            navigateToVerifyOTP = navigateToVerifyOTP
        )
    }
}