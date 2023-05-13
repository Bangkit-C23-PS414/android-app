package com.bangkit.coffee.ui.signin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class SignInCoordinator(
    val viewModel: SignInViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun doStuff() {
        // TODO Handle UI Action
    }
}

@Composable
fun rememberSignInCoordinator(
    viewModel: SignInViewModel = hiltViewModel()
): SignInCoordinator {
    return remember(viewModel) {
        SignInCoordinator(
            viewModel = viewModel
        )
    }
}