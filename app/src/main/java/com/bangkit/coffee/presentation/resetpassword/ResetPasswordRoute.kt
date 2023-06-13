package com.bangkit.coffee.presentation.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalRecoffeeryAppActions

@Composable
fun ResetPasswordRoute(
    coordinator: ResetPasswordCoordinator = rememberResetPasswordCoordinator(),
    navigateToSignIn: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberResetPasswordActions(coordinator, navigateToSignIn)

    // Handle events
    val appActions = LocalRecoffeeryAppActions.current
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message ->
                appActions.showToast(message)
            }
        }
    }
    if(uiState.changeSucceed) {
        LaunchedEffect(Unit) {
            actions.navigateToSignIn()
        }
    }

    // UI Rendering
    ResetPasswordScreen(uiState, actions)
}


@Composable
fun rememberResetPasswordActions(
    coordinator: ResetPasswordCoordinator,
    navigateToSignIn: () -> Unit
): ResetPasswordActions {
    return remember(coordinator) {
        ResetPasswordActions(
            resetPassword = coordinator::resetPassword,
            navigateToSignIn = navigateToSignIn
        )
    }
}