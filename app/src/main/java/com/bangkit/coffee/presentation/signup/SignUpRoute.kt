package com.bangkit.coffee.presentation.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalKopintarAppActions

@Composable
fun SignUpRoute(
    coordinator: SignUpCoordinator = rememberSignUpCoordinator(),
    navigateToSignIn: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberSignUpActions(coordinator, navigateToSignIn)

    // Handle events
    val appActions = LocalKopintarAppActions.current
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message ->
                appActions.showToast(message)
            }
        }
    }
    if (uiState.signedUp) {
        LaunchedEffect(Unit) {
            actions.navigateToSignIn()
        }
    }

    // UI Rendering
    SignUpScreen(uiState, actions)
}


@Composable
fun rememberSignUpActions(
    coordinator: SignUpCoordinator,
    navigateToSignIn: () -> Unit
): SignUpActions {
    return remember(coordinator) {
        SignUpActions(
            signUp = coordinator::signUp,
            navigateToSignIn = navigateToSignIn
        )
    }
}