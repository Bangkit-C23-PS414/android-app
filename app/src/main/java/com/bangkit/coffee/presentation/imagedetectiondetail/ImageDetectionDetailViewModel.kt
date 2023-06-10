package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.domain.usecase.ImageDetectionWithDiseaseUseCase
import com.bangkit.coffee.shared.wrapper.Event
import com.bangkit.coffee.shared.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
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
        viewModelScope.launch {
            launch { loadFromDB() }
            launch { periodicRefresh() }
        }
    }

    private suspend fun loadFromDB() {
        imageDetectionWithDiseaseUseCase.getStream(id)
            .flowOn(Dispatchers.IO)
            .collect { (imageDetection, disease) ->
                _stateFlow.update {
                    it.copy(
                        refreshing = false,
                        waiting = !imageDetection.isDetected,
                        imageDetection = imageDetection,
                        disease = disease
                    )
                }
            }
    }

    private suspend fun periodicRefresh() {
        _stateFlow.update { it.copy(waiting = true) }

        while (true) {
            try {
                when (val response = imageDetectionWithDiseaseUseCase.refreshOne(id)) {
                    is Resource.Error -> {
                        _stateFlow.update {
                            it.copy(message = Event(response.message))
                        }
                    }

                    is Resource.Success -> {
                        if (response.data.isDetected) {
                            break
                        }
                    }
                }
            } catch (e: Exception) {
                _stateFlow.update {
                    it.copy(message = Event("Failed to refresh periodically"))
                }
            } finally {
                delay(2000)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(refreshing = true) }

            val response = imageDetectionWithDiseaseUseCase.refreshOne(id)
            if (response is Resource.Error) {
                _stateFlow.update {
                    it.copy(
                        refreshing = false,
                        message = Event(response.message)
                    )
                }
            }
        }
    }
}