package com.bangkit.coffee.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bangkit.coffee.navigation.Router
import com.bangkit.coffee.shared.components.KopintarNavigationBar
import com.bangkit.coffee.shared.components.KopintarTopAppBar
import com.bangkit.coffee.shared.theme.AppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun KopintarAppScreen(
    state: KopintarAppState = KopintarAppState(),
    actions: KopintarAppActions = KopintarAppActions(),
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect {
            actions.onBackStackEntryChanged(it)
        }
    }

    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Remember a SystemUiController
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()
            val surfaceColor = MaterialTheme.colorScheme.surface

            DisposableEffect(systemUiController, useDarkIcons) {
                systemUiController.setSystemBarsColor(
                    color = surfaceColor,
                    darkIcons = useDarkIcons
                )
                onDispose {}
            }

            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                topBar = {
                    if (state.shouldShowTopAppBar) {
                        KopintarTopAppBar(
                            title = state.topBarTitle
                        )
                    }
                },
                bottomBar = {
                    if (state.shouldShowNavigationBar) {
                        KopintarNavigationBar(navController = navController)
                    }
                }
            ) { contentPadding ->
                ProvideKopintarAppActions(actions = actions) {
                    Router(
                        modifier = Modifier.padding(contentPadding),
                        navController = navController,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(name = "KopintarApp")
private fun KopintarAppScreenPreview() {
    KopintarAppScreen()
}
