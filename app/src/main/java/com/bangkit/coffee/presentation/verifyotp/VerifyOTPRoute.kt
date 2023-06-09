package com.bangkit.coffee.presentation.verifyotp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalKopintarAppActions

@Composable
fun VerifyOTPRoute(
    coordinator: VerifyOTPCoordinator = rememberVerifyOTPCoordinator(),
    email: String?,
    navigateToResetPassword: (String) -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberVerifyOTPActions(coordinator, navigateToResetPassword)

    // Handle events
    val appActions = LocalKopintarAppActions.current
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message ->
                appActions.showToast(message)
            }
        }
    }
    if(uiState.verified) {
        LaunchedEffect(Unit) {
            uiState.token?.let { actions.navigateToResetPassword(it) }
        }
    }

    // UI Rendering
    VerifyOTPScreen(email, uiState, actions)
}


@Composable
fun rememberVerifyOTPActions(
    coordinator: VerifyOTPCoordinator,
    navigateToResetPassword: (String) -> Unit
): VerifyOTPActions {
    return remember(coordinator) {
        VerifyOTPActions(
            verifyOTP = coordinator::verifyOTP,
            navigateToResetPassword = navigateToResetPassword
        )
    }
}