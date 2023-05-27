package com.bangkit.coffee.presentation.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CameraRoute(
    coordinator: CameraCoordinator = rememberCameraCoordinator(),
    navigateUp: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberCameraActions(coordinator, navigateUp)

    // UI Rendering
    CameraScreen(uiState, actions)
}


@Composable
fun rememberCameraActions(
    coordinator: CameraCoordinator,
    navigateUp: () -> Unit
): CameraActions {
    return remember(coordinator) {
        CameraActions(
            navigateUp = navigateUp,
            toggleFlash = coordinator::toggleFlash,
            capture = coordinator::capture,
            cancelCapture = coordinator::cancelCapture,
            setImage = coordinator::setImage,
            clearImage = coordinator::clearImage
        )
    }
}