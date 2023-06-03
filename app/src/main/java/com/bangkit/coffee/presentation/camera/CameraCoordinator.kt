package com.bangkit.coffee.presentation.camera

import android.net.Uri
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

    fun capturing() = viewModel.capturing()

    fun cancelCapturing() = viewModel.cancelCapturing()

    fun setImage(uri: Uri) = viewModel.setImage(uri)

    fun clearImage() = viewModel.clearImage()

    fun uploadImage() = viewModel.uploadImage()

    fun toggleLocalClassifier(state: Boolean) = viewModel.toggleLocalClassifier(state)

    fun setLocalClassifierResult(result: LocalClassifierResult) {
        viewModel.setLocalClassifierResult(result)
    }
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