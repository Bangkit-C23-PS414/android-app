package com.bangkit.coffee.presentation.history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        Timber.d("History init")
    }

    private val _stateFlow: MutableStateFlow<HistoryState> = MutableStateFlow(HistoryState())

    val stateFlow: StateFlow<HistoryState> = _stateFlow.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        Timber.d("History onCleared")
    }
}