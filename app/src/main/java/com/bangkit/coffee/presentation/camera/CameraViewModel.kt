package com.bangkit.coffee.presentation.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.domain.usecase.CropImageUseCase
import com.bangkit.coffee.shared.wrapper.Event
import com.bangkit.coffee.shared.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val imageDetectionRepository: ImageDetectionRepository,
    private val cropImageUseCase: CropImageUseCase
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(CameraState())
    val stateFlow = _stateFlow.asStateFlow()

    /* Camera functionality */
    fun toggleFlash(isFlashOn: Boolean) = _stateFlow.update { it.copy(isFlashOn = isFlashOn) }

    fun capturing() = _stateFlow.update { it.copy(isCapturing = true) }

    fun cancelCapturing() = _stateFlow.update { it.copy(isCapturing = false) }

    /* Preview functionality */
    fun setImage(uri: Uri) = _stateFlow.update {
        it.copy(image = uri, isCapturing = false)
    }

    fun clearImage() = _stateFlow.update {
        it.copy(image = null, isCapturing = false)
    }

    /* Network functionality */
    fun uploadImage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uri = _stateFlow.value.image ?: return@launch
                // Set uploading state
                _stateFlow.update { it.copy(inProgress = true) }

                // Crop image from Uri
                val file = cropImageUseCase(uri)

                // Upload image
                when (val resource = imageDetectionRepository.create(file)) {
                    is Resource.Error -> {
                        // Show error
                        _stateFlow.update {
                            it.copy(
                                inProgress = false,
                                message = Event(resource.message)
                            )
                        }
                    }

                    is Resource.Success -> {
                        // Navigate to history
                        _stateFlow.update { it.copy(inProgress = false, uploaded = true) }
                    }
                }
            } catch (e: Exception) {
                _stateFlow.update {
                    it.copy(
                        inProgress = false,
                        message = Event("Something went wrong")
                    )
                }
            }
        }
    }
}