package com.bangkit.coffee.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.bangkit.coffee.presentation.history.components.FilterHistoryForm

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class HistoryCoordinator(
    val viewModel: HistoryViewModel
) {
    val screenStateFlow = viewModel.stateFlow

    val screenEventFlow = viewModel.eventFlow

    fun toggleFilter() = viewModel.toggleFilter()

    fun applyFilter(formData: FilterHistoryForm) = viewModel.applyFilter(formData)

    fun resetFilter() = viewModel.resetFilter()
}

@Composable
fun rememberHistoryCoordinator(
    viewModel: HistoryViewModel = hiltViewModel()
): HistoryCoordinator {
    return remember(viewModel) {
        HistoryCoordinator(
            viewModel = viewModel
        )
    }
}