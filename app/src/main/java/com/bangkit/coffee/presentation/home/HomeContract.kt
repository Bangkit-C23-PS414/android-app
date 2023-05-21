package com.bangkit.coffee.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf


/**
 * UI State that represents HomeScreen
 **/
class HomeState

/**
 * Home Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class HomeActions(
    val onClick: () -> Unit = {}
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

