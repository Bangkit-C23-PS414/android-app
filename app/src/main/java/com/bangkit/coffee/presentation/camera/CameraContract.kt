package com.bangkit.coffee.presentation.camera

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.shared.wrapper.Event


/**
 * UI State that represents CameraScreen
 **/
data class CameraState(
    val message: Event<String>? = null,
    val isFlashOn: Boolean = false,
    val image: Uri? = null,
    val isCapturing: Boolean = false,
    val inProgress: Boolean = false,
    val imageDetection: ImageDetection? = null,
)

/**
 * Camera Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CameraActions(
    val navigateUp: () -> Unit = {},
    val navigateToDetail: (String) -> Unit = {},
    val toggleFlash: (Boolean) -> Unit = {},
    val capturing: () -> Unit = {},
    val cancelCapturing: () -> Unit = {},
    val setImage: (Uri) -> Unit = {},
    val clearImage: () -> Unit = {},
    val uploadImage: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalCameraActions = staticCompositionLocalOf<CameraActions> {
    error("{NAME} Actions Were not provided, make sure ProvideCameraActions is called")
}

@Composable
fun ProvideCameraActions(actions: CameraActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalCameraActions provides actions) {
        content.invoke()
    }
}

