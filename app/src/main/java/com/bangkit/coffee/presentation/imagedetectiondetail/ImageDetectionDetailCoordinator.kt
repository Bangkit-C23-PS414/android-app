package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class ImageDetectionDetailCoordinator(
    val viewModel: ImageDetectionDetailViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    val screenEventFlow = viewModel.eventFlow

    fun refresh() = viewModel.refresh()
}

@Composable
fun rememberImageDetectionDetailCoordinator(
    viewModel: ImageDetectionDetailViewModel = hiltViewModel()
): ImageDetectionDetailCoordinator {
    return remember(viewModel) {
        ImageDetectionDetailCoordinator(
            viewModel = viewModel
        )
    }
}