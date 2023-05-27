package com.bangkit.coffee.presentation.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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
    LaunchedEffect(Unit) {
        coordinator.screenEventFlow.collect { event ->
            when (event) {
                else -> {}
            }
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
            navigateToLogin = navigateToLogin
        )
    }
}