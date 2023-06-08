package com.bangkit.coffee.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class SplashCoordinator(
    val viewModel: SplashViewModel
) {
    val screenStateFlow = viewModel.stateFlow
}

@Composable
fun rememberSplashCoordinator(
    viewModel: SplashViewModel = hiltViewModel()
): SplashCoordinator {
    return remember(viewModel) {
        SplashCoordinator(
            viewModel = viewModel
        )
    }
}