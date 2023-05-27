package com.bangkit.coffee.presentation.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents ResetPasswordScreen
 **/
class ResetPasswordState

/**
 * UI Event that represents ResetPasswordScreen
 */
sealed class ResetPasswordEvent {

}

/**
 * ResetPassword Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class ResetPasswordActions(
    val navigateToLogin: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalResetPasswordActions = staticCompositionLocalOf<ResetPasswordActions> {
    error("{NAME} Actions Were not provided, make sure ProvideResetPasswordActions is called")
}

@Composable
fun ProvideResetPasswordActions(actions: ResetPasswordActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalResetPasswordActions provides actions) {
        content.invoke()
    }
}

