package com.bangkit.coffee.presentation.resetpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.shared.wrapper.Event


/**
 * UI State that represents ResetPasswordScreen
 **/
data class ResetPasswordState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val changeSucceed: Boolean = false
)

/**
 * ResetPassword Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class ResetPasswordActions(
    val resetPassword: (String) -> Unit = {},
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

