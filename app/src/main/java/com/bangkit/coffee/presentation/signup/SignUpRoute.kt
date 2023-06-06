package com.bangkit.coffee.presentation.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalRecoffeeryAppActions

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
    val appActions = LocalRecoffeeryAppActions.current
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message ->
                appActions.showToast(message)
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
            signUp = coordinator::signUp,
            navigateToDashboard = navigateToDashboard
        )
    }
}