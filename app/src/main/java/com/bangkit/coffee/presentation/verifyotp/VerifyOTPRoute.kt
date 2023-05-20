package com.bangkit.coffee.presentation.verifyotp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun VerifyOTPRoute(
    coordinator: VerifyOTPCoordinator = rememberVerifyOTPCoordinator(),
    navigateUp: () -> Unit = {},
    navigateToResetPassword: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(VerifyOTPState())

    // UI Actions
    val actions = rememberVerifyOTPActions(coordinator, navigateUp, navigateToResetPassword)

    // UI Rendering
    VerifyOTPScreen(uiState, actions)
}


@Composable
fun rememberVerifyOTPActions(
    coordinator: VerifyOTPCoordinator,
    navigateUp: () -> Unit,
    navigateToResetPassword: () -> Unit
): VerifyOTPActions {
    return remember(coordinator) {
        VerifyOTPActions(
            verifyOTP = coordinator::verifyOTP,
            navigateUp = navigateUp,
            navigateToResetPassword = navigateToResetPassword
        )
    }
}