package com.bangkit.coffee.presentation.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignUpRoute(
    coordinator: SignUpCoordinator = rememberSignUpCoordinator(),
    navigateToDashboard: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberSignUpActions(coordinator, navigateToDashboard)

    // Handle events
    LaunchedEffect(Unit) {
        coordinator.screenEventFlow.collect { event ->
            when (event) {
                else -> {}
            }
        }
    }

    // UI Rendering
    SignUpScreen(uiState, actions)
}


@Composable
fun rememberSignUpActions(
    coordinator: SignUpCoordinator,
    navigateToDashboard: () -> Unit
): SignUpActions {
    return remember(coordinator) {
        SignUpActions(
            navigateToDashboard = navigateToDashboard
        )
    }
}