package com.bangkit.coffee.presentation.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalKopintarAppActions

@Composable
fun ResetPasswordRoute(
    coordinator: ResetPasswordCoordinator = rememberResetPasswordCoordinator(),
    navigateToLogin: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberResetPasswordActions(coordinator, navigateToLogin)

    // Handle events
    val appActions = LocalKopintarAppActions.current
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message ->
                appActions.showToast(message)
            }
        }
    }
    if(uiState.changeSucceed) {
        LaunchedEffect(Unit) {
            actions.navigateToLogin()
        }
    }

    // UI Rendering
    ResetPasswordScreen(uiState, actions)
}


@Composable
fun rememberResetPasswordActions(
    coordinator: ResetPasswordCoordinator,
    navigateToLogin: () -> Unit
): ResetPasswordActions {
    return remember(coordinator) {
        ResetPasswordActions(
            resetPassword = coordinator::resetPassword,
            navigateToLogin = navigateToLogin
        )
    }
}