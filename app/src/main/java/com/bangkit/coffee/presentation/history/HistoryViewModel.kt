package com.bangkit.coffee.presentation.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bangkit.coffee.domain.ImageDetectionDummies
import com.bangkit.coffee.domain.entity.ImageDetection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val imageDetections: List<ImageDetection> = ImageDetectionDummies

    private val _stateFlow: MutableStateFlow<HistoryState> = MutableStateFlow(HistoryState(
        imageDetections = imageDetections
    ))

    val stateFlow: StateFlow<HistoryState> = _stateFlow.asStateFlow()
}