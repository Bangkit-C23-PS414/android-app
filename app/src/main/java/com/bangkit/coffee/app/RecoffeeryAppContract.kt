package com.bangkit.coffee.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination


/**
 * UI State that represents RecoffeeryAppScreen
 **/
data class RecoffeeryAppState(
    val currentDestination: NavDestination? = null,
    val currentRoute: String? = null,
    val shouldShowTopAppBar: Boolean = false,
    val shouldShowNavigationBar: Boolean = false,
    val topBarTitle: String = "",
    val token: String? = null,
    val isValid: Boolean = false,
)

/**
 * RecoffeeryApp Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class RecoffeeryAppActions(
    val onBackStackEntryChanged: (NavBackStackEntry) -> Unit = {},
    val showToast: (String) -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalRecoffeeryAppActions = staticCompositionLocalOf<RecoffeeryAppActions> {
    error("{NAME} Actions Were not provided, make sure ProvideRecoffeeryAppActions is called")
}

@Composable
fun ProvideRecoffeeryAppActions(actions: RecoffeeryAppActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalRecoffeeryAppActions provides actions) {
        content.invoke()
    }
}

