package com.bangkit.coffee.presentation.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bangkit.coffee.domain.ImageDetectionDummies
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.presentation.history.components.FilterHistoryForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val imageDetections: List<ImageDetection> = ImageDetectionDummies

    private val _eventFlow = Channel<HistoryEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _stateFlow = MutableStateFlow(HistoryState(imageDetections = imageDetections))
    val stateFlow = _stateFlow.asStateFlow()

    fun toggleFilter() {
        _stateFlow.update {
            it.copy(filterVisible = !it.filterVisible)
        }
    }

    fun applyFilter(formData: FilterHistoryForm) {
        _stateFlow.update {
            it.copy(filterFormData = formData, filterVisible = false)
        }
    }

    fun resetFilter() = applyFilter(FilterHistoryForm())
}