package com.bangkit.coffee.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.domain.entity.Disease
import com.bangkit.coffee.presentation.home.components.DetectionStep


/**
 * UI State that represents HomeScreen
 **/
data class HomeState(
    val detectionSteps: List<DetectionStep> = emptyList(),
    val diseases: List<Disease> = emptyList()
)

/**
 * Home Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class HomeActions(
    val navigateToCamera: () -> Unit = {},
    val navigateToDetailDisease: (String) -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalHomeActions = staticCompositionLocalOf<HomeActions> {
    error("{NAME} Actions Were not provided, make sure ProvideHomeActions is called")
}

@Composable
fun ProvideHomeActions(actions: HomeActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalHomeActions provides actions) {
        content.invoke()
    }
}

