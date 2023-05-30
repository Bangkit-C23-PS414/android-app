package com.bangkit.coffee.app

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RecoffeeryApp(
    coordinator: RecoffeeryAppCoordinator = rememberRecoffeeryAppCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle(RecoffeeryAppState())

    // UI Actions
    val actions = rememberRecoffeeryAppActions(coordinator)

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
    RecoffeeryAppScreen(uiState, actions)
}


@Composable
fun rememberRecoffeeryAppActions(coordinator: RecoffeeryAppCoordinator): RecoffeeryAppActions {
    return remember(coordinator) {
        RecoffeeryAppActions(
            onBackStackEntryChanged = coordinator::onBackStackEntryChanged,
            showToast = coordinator::showToast
        )
    }
}