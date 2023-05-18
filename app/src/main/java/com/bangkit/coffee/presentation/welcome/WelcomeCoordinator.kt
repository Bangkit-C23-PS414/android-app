package com.bangkit.coffee.presentation.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class WelcomeCoordinator(
    val viewModel: WelcomeViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun doStuff() {
        // TODO Handle UI Action
    }
}

@Composable
fun rememberWelcomeCoordinator(
    viewModel: WelcomeViewModel = hiltViewModel()
): WelcomeCoordinator {
    return remember(viewModel) {
        WelcomeCoordinator(
            viewModel = viewModel
        )
    }
}