package com.bangkit.coffee.presentation.diseasedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class DiseaseDetailCoordinator(
    val viewModel: DiseaseDetailViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    val screenEventFlow = viewModel.eventFlow
}

@Composable
fun rememberDiseaseDetailCoordinator(
    viewModel: DiseaseDetailViewModel = hiltViewModel()
): DiseaseDetailCoordinator {
    return remember(viewModel) {
        DiseaseDetailCoordinator(
            viewModel = viewModel
        )
    }
}