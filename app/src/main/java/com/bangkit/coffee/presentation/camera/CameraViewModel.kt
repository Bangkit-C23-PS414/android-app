package com.bangkit.coffee.presentation.camera

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = Channel<CameraEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _stateFlow = MutableStateFlow(CameraState())
    val stateFlow = _stateFlow.asStateFlow()

    fun toggleFlash(isFlashOn: Boolean) = _stateFlow.update { it.copy(isFlashOn = isFlashOn) }

    fun capture() = _stateFlow.update { it.copy(isCapturing = true) }

    fun cancelCapture() = _stateFlow.update { it.copy(isCapturing = false) }

    fun setImage(uri: Uri) = _stateFlow.update {
        it.copy(image = uri, isCapturing = false)
    }

    fun clearImage() = _stateFlow.update {
        it.copy(image = null, isCapturing = false)
    }
}