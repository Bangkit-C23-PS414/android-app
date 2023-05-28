package com.bangkit.coffee.presentation.diseasedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DiseaseDetailRoute(
    coordinator: DiseaseDetailCoordinator = rememberDiseaseDetailCoordinator(),
    navigateUp: () -> Unit = {}
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsStateWithLifecycle()

    // UI Actions
    val actions = rememberDiseaseDetailActions(coordinator, navigateUp)

    // UI Rendering
    DiseaseDetailScreen(uiState, actions)
}


@Composable
fun rememberDiseaseDetailActions(
    coordinator: DiseaseDetailCoordinator,
    navigateUp: () -> Unit
): DiseaseDetailActions {
    return remember(coordinator) {
        DiseaseDetailActions(
            navigateUp = navigateUp
        )
    }
}