package com.bangkit.coffee.presentation.verifyotp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun VerifyOTPRoute(
    coordinator: VerifyOTPCoordinator = rememberVerifyOTPCoordinator(),
    navigateToResetPassword: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberVerifyOTPActions(coordinator, navigateToResetPassword)

    // UI Rendering
    VerifyOTPScreen(uiState, actions)
}


@Composable
fun rememberVerifyOTPActions(
    coordinator: VerifyOTPCoordinator,
    navigateToResetPassword: () -> Unit
): VerifyOTPActions {
    return remember(coordinator) {
        VerifyOTPActions(
            verifyOTP = coordinator::verifyOTP,
            navigateToResetPassword = navigateToResetPassword
        )
    }
}