package com.bangkit.coffee.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents CameraScreen
 **/
data class CameraState(
    val isFlashOn: Boolean = false,
)

/**
 * Camera Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CameraActions(
    val navigateUp: () -> Unit = {},
    val toggleFlash: (Boolean) -> Unit = {}
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

