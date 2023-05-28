package com.bangkit.coffee.app

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun KopintarApp(
    coordinator: KopintarAppCoordinator = rememberKopintarAppCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(KopintarAppState())

    // UI Actions
    val actions = rememberKopintarAppActions(coordinator)

    // Toast
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        coordinator
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }

    // UI Rendering
    KopintarAppScreen(uiState, actions)
}


@Composable
fun rememberKopintarAppActions(coordinator: KopintarAppCoordinator): KopintarAppActions {
    return remember(coordinator) {
        KopintarAppActions(
            onBackStackEntryChanged = coordinator::onBackStackEntryChanged,
            showToast = coordinator::showToast
        )
    }
}