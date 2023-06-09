package com.bangkit.coffee.presentation.forgotpassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class ForgotPasswordCoordinator(
    val viewModel: ForgotPasswordViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun forgotPassword(email: String) {
        viewModel.forgotPassword(email)
    }
}

@Composable
fun rememberForgotPasswordCoordinator(
    viewModel: ForgotPasswordViewModel = hiltViewModel()
): ForgotPasswordCoordinator {
    return remember(viewModel) {
        ForgotPasswordCoordinator(
            viewModel = viewModel
        )
    }
}