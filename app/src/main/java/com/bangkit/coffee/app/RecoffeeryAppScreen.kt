package com.bangkit.coffee.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bangkit.coffee.app.components.RecoffeeryNavigationBar
import com.bangkit.coffee.app.components.RecoffeeryTopAppBar
import com.bangkit.coffee.navigation.Router
import com.bangkit.coffee.shared.theme.AppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun RecoffeeryAppScreen(
    state: RecoffeeryAppState = RecoffeeryAppState(),
    actions: RecoffeeryAppActions = RecoffeeryAppActions(),
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect {
            actions.onBackStackEntryChanged(it)
        }
    }

    AppTheme {
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
                    RecoffeeryTopAppBar(
                        title = state.topBarTitle
                    )
                }
            },
            bottomBar = {
                if (state.shouldShowNavigationBar) {
                    RecoffeeryNavigationBar(navController = navController)
                }
            }
        ) { contentPadding ->
            ProvideRecoffeeryAppActions(actions = actions) {
                Router(
                    modifier = Modifier.padding(contentPadding),
                    navController = navController,
                )
            }
        }
    }
}

@Composable
@Preview(name = "RecoffeeryApp")
private fun RecoffeeryAppScreenPreview() {
    RecoffeeryAppScreen()
}

