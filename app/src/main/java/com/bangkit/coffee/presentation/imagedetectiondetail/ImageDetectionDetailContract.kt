package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.domain.entity.Disease
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.shared.wrapper.Event


/**
 * UI State that represents ImageDetectionDetailScreen
 **/
data class ImageDetectionDetailState(
    val refreshing: Boolean = false,
    val imageDetection: ImageDetection? = null,
    val disease: Disease? = null,
    val message: Event<String>? = null,
    val waiting: Boolean = false
)

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

