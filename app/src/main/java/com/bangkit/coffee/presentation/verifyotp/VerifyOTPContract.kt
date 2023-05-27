package com.bangkit.coffee.presentation.verifyotp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents VerifyOTPScreen
 **/
class VerifyOTPState

/**
 * UI Event that represents VerifyOTPScreen
 */
sealed class VerifyOTPEvent {

}

/**
 * VerifyOTP Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class VerifyOTPActions(
    val verifyOTP: () -> Unit = {},
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

