package com.bangkit.coffee.presentation.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents SignUpScreen
 **/
data class SignUpState(
    val isLoading: Boolean = false,
)

/**
 * SignUp Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class SignUpActions(
    val signUp: () -> Unit = {},
    val setPasswordVisibility: () -> Unit = {},
    val navigateToDashboard: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalSignUpActions = staticCompositionLocalOf<SignUpActions> {
    error("{NAME} Actions Were not provided, make sure ProvideSignUpActions is called")
}

@Composable
fun ProvideSignUpActions(actions: SignUpActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalSignUpActions provides actions) {
        content.invoke()
    }
}

