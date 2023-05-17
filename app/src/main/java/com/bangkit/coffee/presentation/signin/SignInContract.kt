package com.bangkit.coffee.presentation.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * UI State that represents SignInScreen
 **/
data class SignInState(
    val isLoading: Boolean = false,
)

/**
 * SignIn Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class SignInActions(
    val onClick: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalSignInActions = staticCompositionLocalOf<SignInActions> {
    error("{NAME} Actions Were not provided, make sure ProvideSignInActions is called")
}

@Composable
fun ProvideSignInActions(actions: SignInActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalSignInActions provides actions) {
        content.invoke()
    }
}

