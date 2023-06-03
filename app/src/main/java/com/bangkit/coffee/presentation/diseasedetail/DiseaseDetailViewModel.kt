package com.bangkit.coffee.presentation.diseasedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bangkit.coffee.data.repository.DiseaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DiseaseDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    diseaseRepository: DiseaseRepository
) : ViewModel() {

    val id = checkNotNull<String>(savedStateHandle["id"])

    private val _stateFlow = MutableStateFlow(
        DiseaseDetailState(
            disease = diseaseRepository.getOne(id)
        )
    )
    val stateFlow = _stateFlow.asStateFlow()
}