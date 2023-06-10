package com.bangkit.coffee.presentation.verifyotp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalRecoffeeryAppActions

@Composable
fun VerifyOTPRoute(
    coordinator: VerifyOTPCoordinator = rememberVerifyOTPCoordinator(),
    navigateToResetPassword: (String) -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberVerifyOTPActions(coordinator, navigateToResetPassword)

    // Handle events
    val appActions = LocalRecoffeeryAppActions.current
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message ->
                appActions.showToast(message)
            }
        }
    }
    if(uiState.verified) {
        uiState.token?.let {
            LaunchedEffect(it) {
                it.getContentIfNotHandled()?.let { token ->
                    actions.navigateToResetPassword(token)
                }
            }
        }
    }

    // UI Rendering
    VerifyOTPScreen(uiState, actions)
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