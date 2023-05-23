package com.bangkit.coffee.presentation.camera

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<CameraState> = MutableStateFlow(CameraState())

    val stateFlow: StateFlow<CameraState> = _stateFlow.asStateFlow()

    fun toggleFlash(isFlashOn: Boolean) {
        _stateFlow.update {
            it.copy(isFlashOn = isFlashOn)
        }
    }
}