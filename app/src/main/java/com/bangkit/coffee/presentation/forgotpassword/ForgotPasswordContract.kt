package com.bangkit.coffee.presentation.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.shared.wrapper.Event


/**
 * UI State that represents ForgotPasswordScreen
 **/
data class ForgotPasswordState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val emailInput: String? = null,
)

/**
 * ForgotPassword Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class ForgotPasswordActions(
    val forgotPassword: (String) -> Unit = {},
    val navigateToVerifyOTP: (String) -> Unit = {},
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalForgotPasswordActions = staticCompositionLocalOf<ForgotPasswordActions> {
    error("{NAME} Actions Were not provided, make sure ProvideForgotPasswordActions is called")
}

@Composable
fun ProvideForgotPasswordActions(actions: ForgotPasswordActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalForgotPasswordActions provides actions) {
        content.invoke()
    }
}

