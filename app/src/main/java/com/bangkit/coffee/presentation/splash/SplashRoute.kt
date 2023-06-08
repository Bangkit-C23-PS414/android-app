package com.bangkit.coffee.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SplashRoute(
    coordinator: SplashCoordinator = rememberSplashCoordinator(),
    navigateToWelcome: () -> Unit = {},
    navigateToHome: () -> Unit = {},
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(SplashState())

    // UI Actions
    val actions = rememberSplashActions(coordinator, navigateToWelcome, navigateToHome)

    // Handle navigation
    LaunchedEffect(uiState.authenticated) {
        if (uiState.authenticated == true) {
            actions.navigateToHome()
        } else if (uiState.authenticated == false) {
            actions.navigateToWelcome()
        }
    }

    // UI Rendering
    SplashScreen(uiState, actions)
}


@Composable
fun rememberSplashActions(
    coordinator: SplashCoordinator,
    navigateToWelcome: () -> Unit,
    navigateToHome: () -> Unit
): SplashActions {
    return remember(coordinator) {
        SplashActions(
            navigateToWelcome = navigateToWelcome,
            navigateToHome = navigateToHome
        )
    }
}