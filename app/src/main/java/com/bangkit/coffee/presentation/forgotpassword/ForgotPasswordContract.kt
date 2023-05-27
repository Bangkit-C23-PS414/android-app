package com.bangkit.coffee.presentation.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents ForgotPasswordScreen
 **/
class ForgotPasswordState

/**
 * UI Event that represents ForgotPasswordScreen
 */
sealed class ForgotPasswordEvent {

}

/**
 * ForgotPassword Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class ForgotPasswordActions(
    val forgotPassword: () -> Unit = {},
    val navigateToVerifyOTP: () -> Unit = {}
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

