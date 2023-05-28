package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.domain.ImageDetectionDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetectionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val imageDetectionId = checkNotNull<String>(savedStateHandle["id"])

    private val _eventFlow = Channel<ImageDetectionDetailEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _stateFlow = MutableStateFlow<ImageDetectionDetailState>(ImageDetectionDetailState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _stateFlow.update {
                /*ImageDetectionDetailState.Healthy(
                    ImageDetectionDummy,
                )*/
                ImageDetectionDetailState.Sick(
                    ImageDetectionDummy,
                    DiseaseDummy
                )
            }
        }
    }

    fun refresh() {

    }
}