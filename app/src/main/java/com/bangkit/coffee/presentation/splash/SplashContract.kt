package com.bangkit.coffee.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents SplashScreen
 **/
data class SplashState(
    val authenticated: Boolean? = null
)

/**
 * Splash Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class SplashActions(
    val navigateToWelcome: () -> Unit = {},
    val navigateToHome: () -> Unit = {},
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalSplashActions = staticCompositionLocalOf<SplashActions> {
    error("{NAME} Actions Were not provided, make sure ProvideSplashActions is called")
}

@Composable
fun ProvideSplashActions(actions: SplashActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalSplashActions provides actions) {
        content.invoke()
    }
}

