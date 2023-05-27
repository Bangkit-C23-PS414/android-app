package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.domain.entity.Disease
import com.bangkit.coffee.domain.entity.ImageDetection


/**
 * UI State that represents ImageDetectionDetailScreen
 **/
sealed class ImageDetectionDetailState {
    object Loading : ImageDetectionDetailState()
    object Empty : ImageDetectionDetailState()

    class Healthy(val imageDetection: ImageDetection) : ImageDetectionDetailState()
    class Sick(
        val imageDetection: ImageDetection,
        val disease: Disease
    ) : ImageDetectionDetailState()
}

/**
 * UI Event that represents ImageDetectionDetailScreen
 */
sealed class ImageDetectionDetailEvent {

}

/**
 * ImageDetectionDetail Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class ImageDetectionDetailActions(
    val navigateUp: () -> Unit = {},
    val refresh: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalImageDetectionDetailActions = staticCompositionLocalOf<ImageDetectionDetailActions> {
    error("{NAME} Actions Were not provided, make sure ProvideImageDetectionDetailActions is called")
}

@Composable
fun ProvideImageDetectionDetailActions(
    actions: ImageDetectionDetailActions,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalImageDetectionDetailActions provides actions) {
        content.invoke()
    }
}

