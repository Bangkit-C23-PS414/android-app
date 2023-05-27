package com.bangkit.coffee.presentation.signup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = Channel<SignUpEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _stateFlow = MutableStateFlow(SignUpState())
    val stateFlow = _stateFlow.asStateFlow()

}