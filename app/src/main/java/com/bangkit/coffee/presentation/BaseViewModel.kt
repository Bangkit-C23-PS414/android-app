package com.bangkit.coffee.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    // How to subscribe message into toast
    // LaunchedEffect(Unit) {
    //     viewModel
    //         .toastMessage
    //         .collect { message ->
    //             Toast.makeText(
    //                 context,
    //                 message,
    //                 Toast.LENGTH_SHORT,
    //             ).show()
    //         }
    // }

    // Mutable/SharedFlow of String resource reference Event
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    // Post in background thread
    fun sendToastMessage(toastMessage: String) {
        viewModelScope.launch {
            _toastMessage.emit(toastMessage)
        }
    }
}