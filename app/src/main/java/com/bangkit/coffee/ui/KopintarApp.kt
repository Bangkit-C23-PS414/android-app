package com.bangkit.coffee.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bangkit.coffee.navigation.Router
import com.bangkit.coffee.ui.theme.AppTheme

@Composable
fun KopintarApp(
    appState: KopintarAppState = rememberKopintarAppState()
) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(appState.snackbarHostState) },
                bottomBar = {
                    if (appState.shouldShowNavigationBar) {
                        KopintarNavigationBar(
                            navController = appState.navController,
                            screenList = appState.navigationBarScreens
                        )
                    }
                }
            ) { contentPadding ->
                Router(
                    modifier = Modifier.padding(contentPadding),
                    navController = appState.navController
                )
            }
        }
    }
}