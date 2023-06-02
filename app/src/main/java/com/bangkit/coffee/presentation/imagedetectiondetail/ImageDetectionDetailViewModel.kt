package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.domain.usecase.ImageDetectionWithDiseaseUseCase
import com.bangkit.coffee.shared.wrapper.Event
import com.bangkit.coffee.shared.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetectionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val imageDetectionWithDiseaseUseCase: ImageDetectionWithDiseaseUseCase,
) : ViewModel() {

    private val id = checkNotNull<String>(savedStateHandle["id"])

    private val _stateFlow = MutableStateFlow(ImageDetectionDetailState())
    val stateFlow = _stateFlow.asStateFlow()

    init {
        hook()
    }

    private fun hook() {
        viewModelScope.launch {
            imageDetectionWithDiseaseUseCase
                .getStream(id)
                .mapNotNull { it }
                .collect { (imageDetection, disease) ->
                    _stateFlow.update {
                        it.copy(
                            loading = false,
                            imageDetection = imageDetection,
                            disease = disease
                        )
                    }

                    // periodic check
                    if (imageDetection?.isDetected == false) {
                        _stateFlow.update { it.copy(waiting = true) }
                        delay(5000)
                        _stateFlow.update { it.copy(waiting = false) }
                        imageDetectionWithDiseaseUseCase.refreshOne(id)
                    }
                }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(loading = true) }

            val response = imageDetectionWithDiseaseUseCase.refreshOne(id)
            if (response is Resource.Error) {
                _stateFlow.update {
                    it.copy(
                        loading = false,
                        message = Event(response.message)
                    )
                }
            }
        }
    }
}