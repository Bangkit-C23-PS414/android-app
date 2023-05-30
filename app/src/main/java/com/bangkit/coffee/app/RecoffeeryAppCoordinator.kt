package com.bangkit.coffee.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class RecoffeeryAppCoordinator(
    val viewModel: RecoffeeryAppViewModel
) {
    val screenStateFlow = viewModel.stateFlow
    val toastMessage = viewModel.toastMessage

    fun onBackStackEntryChanged(navBackStackEntry: NavBackStackEntry) {
        viewModel.onBackStackEntryChanged(navBackStackEntry)
    }

    fun showToast(message: String) = viewModel.showToast(message)
}

@Composable
fun rememberRecoffeeryAppCoordinator(
    viewModel: RecoffeeryAppViewModel = hiltViewModel()
): RecoffeeryAppCoordinator {
    return remember(viewModel) {
        RecoffeeryAppCoordinator(
            viewModel = viewModel
        )
    }
}