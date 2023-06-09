package com.bangkit.coffee.presentation.verifyotp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.shared.wrapper.Event


/**
 * UI State that represents VerifyOTPScreen
 **/
data class VerifyOTPState(
    val loading: Boolean = false,
    val message: Event<String>? = null,
    val verified: Boolean = false
)

/**
 * VerifyOTP Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class VerifyOTPActions(
    val verifyOTP: (String?, String) -> Unit = { _: String?, _: String -> },
    val navigateToResetPassword: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalVerifyOTPActions = staticCompositionLocalOf<VerifyOTPActions> {
    error("{NAME} Actions Were not provided, make sure ProvideVerifyOTPActions is called")
}

@Composable
fun ProvideVerifyOTPActions(actions: VerifyOTPActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalVerifyOTPActions provides actions) {
        content.invoke()
    }
}

