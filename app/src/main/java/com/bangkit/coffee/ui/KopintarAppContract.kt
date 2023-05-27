package com.bangkit.coffee.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination


/**
 * UI State that represents KopintarAppScreen
 **/
data class KopintarAppState(
    val currentDestination: NavDestination? = null,
    val currentRoute: String? = null,
    val shouldShowTopAppBar: Boolean = false,
    val shouldShowNavigationBar: Boolean = false,
    val topBarTitle: String = "",
    val token: String? = null,
    val isValid: Boolean = false,
)

/**
 * KopintarApp Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class KopintarAppActions(
    val onBackStackEntryChanged: (NavBackStackEntry) -> Unit = {},
    val showToast: (String) -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalKopintarAppActions = staticCompositionLocalOf<KopintarAppActions> {
    error("{NAME} Actions Were not provided, make sure ProvideKopintarAppActions is called")
}

@Composable
fun ProvideKopintarAppActions(actions: KopintarAppActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalKopintarAppActions provides actions) {
        content.invoke()
    }
}

