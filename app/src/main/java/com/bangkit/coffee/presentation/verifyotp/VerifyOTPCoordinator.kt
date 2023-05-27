package com.bangkit.coffee.presentation.verifyotp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class VerifyOTPCoordinator(
    val viewModel: VerifyOTPViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun verifyOTP() {

    }
}

@Composable
fun rememberVerifyOTPCoordinator(
    viewModel: VerifyOTPViewModel = hiltViewModel()
): VerifyOTPCoordinator {
    return remember(viewModel) {
        VerifyOTPCoordinator(
            viewModel = viewModel
        )
    }
}