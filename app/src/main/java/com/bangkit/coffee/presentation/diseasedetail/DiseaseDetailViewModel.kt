package com.bangkit.coffee.presentation.diseasedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bangkit.coffee.domain.DiseaseDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DiseaseDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventFlow = Channel<DiseaseDetailEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    private val _stateFlow = MutableStateFlow<DiseaseDetailState>(DiseaseDetailState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        _stateFlow.update {
            DiseaseDetailState.Success(
                disease = DiseaseDummy
            )
        }
    }
}