package com.bangkit.coffee.presentation.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.presentation.signin.components.SignInForm
import com.bangkit.coffee.util.Event

/**
 * UI State that represents SignInScreen
 **/
sealed class SignInState {
    data class Idle(
        val passwordVisible: Boolean = false
    ) : SignInState()

    object InProgress : SignInState()

    data class Error(val message: Event<String>) : SignInState()

    object SignedIn : SignInState()
}

/**
 * SignIn Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class SignInActions(
    val signIn: (SignInForm) -> Unit = {},
    val setPasswordVisibility: (Boolean) -> Unit = {},
    val navigateToForgotPassword: () -> Unit = {},
    val navigateToDashboard: () -> Unit = {},
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

