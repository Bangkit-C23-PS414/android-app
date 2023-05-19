package com.bangkit.coffee.presentation.verifyotp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VerifyOTPViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<VerifyOTPState> = MutableStateFlow(VerifyOTPState())

    val stateFlow: StateFlow<VerifyOTPState> = _stateFlow.asStateFlow()

}