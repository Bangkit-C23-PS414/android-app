package com.bangkit.coffee.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class HomeCoordinator(
    val viewModel: HomeViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    val screenEventFlow = viewModel.eventFlow
}

@Composable
fun rememberHomeCoordinator(
    viewModel: HomeViewModel = hiltViewModel()
): HomeCoordinator {
    return remember(viewModel) {
        HomeCoordinator(
            viewModel = viewModel
        )
    }
}