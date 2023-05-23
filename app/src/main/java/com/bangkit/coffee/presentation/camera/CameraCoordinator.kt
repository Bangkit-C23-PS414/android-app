package com.bangkit.coffee.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class CameraCoordinator(
    val viewModel: CameraViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    fun toggleFlash(isFlashOn: Boolean) = viewModel.toggleFlash(isFlashOn)
}

@Composable
fun rememberCameraCoordinator(
    viewModel: CameraViewModel = hiltViewModel()
): CameraCoordinator {
    return remember(viewModel) {
        CameraCoordinator(
            viewModel = viewModel
        )
    }
}