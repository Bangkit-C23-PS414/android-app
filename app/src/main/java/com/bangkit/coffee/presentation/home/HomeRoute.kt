package com.bangkit.coffee.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bangkit.coffee.R
import com.bangkit.coffee.ui.KopintarTopAppBarState

@Composable
fun HomeRoute(
    coordinator: HomeCoordinator = rememberHomeCoordinator(),
    onComposing: (KopintarTopAppBarState) -> Unit
) {
    val title = stringResource(R.string.app_name)
    LaunchedEffect(Unit) {
        onComposing(KopintarTopAppBarState(title = title))
    }

    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberHomeActions(coordinator)

    // UI Rendering
    HomeScreen(uiState, actions)
}


@Composable
fun rememberHomeActions(coordinator: HomeCoordinator): HomeActions {
    return remember(coordinator) {
        HomeActions(
            onClick = coordinator::doStuff
        )
    }
}