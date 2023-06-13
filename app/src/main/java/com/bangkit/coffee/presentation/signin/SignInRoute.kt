package com.bangkit.coffee.presentation.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.app.LocalRecoffeeryAppActions

@Composable
fun SignInRoute(
    coordinator: SignInCoordinator = rememberSignInCoordinator(),
    navigateToForgotPassword: () -> Unit = {},
    navigateToHome: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberSignInActions(
        coordinator,
        navigateToForgotPassword,
        navigateToHome
    )

    // Handle events
    val appActions = LocalRecoffeeryAppActions.current
    if (uiState.signedIn) {
        LaunchedEffect(Unit) {
            actions.navigateToHome()
        }
    }
    uiState.message?.let { event ->
        LaunchedEffect(event) {
            event.getContentIfNotHandled()?.let { message ->
                appActions.showToast(message)
            }
        }
    }

    // UI Rendering
    SignInScreen(uiState, actions)
}


@Composable
fun rememberSignInActions(
    coordinator: SignInCoordinator,
    navigateToForgotPassword: () -> Unit,
    navigateToHome: () -> Unit
): SignInActions {
    return remember(coordinator) {
        SignInActions(
            signIn = coordinator::signIn,
            setPasswordVisibility = coordinator::setPasswordVisibility,
            navigateToForgotPassword = navigateToForgotPassword,
            navigateToHome = navigateToHome
        )
    }
}