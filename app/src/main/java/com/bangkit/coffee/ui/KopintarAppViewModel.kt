package com.bangkit.coffee.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.bangkit.coffee.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KopintarAppViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<KopintarAppState> =
        MutableStateFlow(KopintarAppState())
    val stateFlow: StateFlow<KopintarAppState> = _stateFlow.asStateFlow()

    fun onBackStackEntryChanged(navBackStackEntry: NavBackStackEntry) {
        _stateFlow.update {
            KopintarAppState(
                currentDestination = navBackStackEntry.destination,
                currentRoute = navBackStackEntry.destination.route,
                shouldShowTopAppBar = navBackStackEntry.destination.route in Screen.Manifest.topBarRoutes,
                shouldShowNavigationBar = navBackStackEntry.destination.route in Screen.Manifest.bottomBarRoutes,
                topBarTitle = getTitle(navBackStackEntry.destination.route)
            )
        }
    }

    private fun getTitle(route: String?): String {
        return "title"
    }

    // Mutable/SharedFlow of String resource reference Event
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    // Post in background thread
    fun showToast(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

}