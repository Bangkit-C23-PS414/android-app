package com.bangkit.coffee.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.coffee.navigation.Screen
import kotlinx.coroutines.CoroutineScope

data class KopintarAppState(
    val coroutineScope: CoroutineScope,
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    // Top app bar
    // private val topAppBarScreenRoutes = listOf(Screen.Home.route, Screen.History.route)
    private val topAppBarScreenRoutes = emptyList<String>()
    val shouldShowTopAppBar: Boolean
        @Composable get() = currentDestination?.route in topAppBarScreenRoutes

    // Bottom navigation bar
    val navigationBarScreens = listOf(Screen.Home, Screen.History, Screen.Profile)
    private val navigationBarScreenRoutes = navigationBarScreens.map { it.route }
    val shouldShowNavigationBar: Boolean
        @Composable get() = currentDestination?.route in navigationBarScreenRoutes
}

@Composable
fun rememberKopintarAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): KopintarAppState {
    return remember(
        coroutineScope,
        navController,
        snackbarHostState,
    ) {
        KopintarAppState(
            coroutineScope,
            navController,
            snackbarHostState
        )
    }
}