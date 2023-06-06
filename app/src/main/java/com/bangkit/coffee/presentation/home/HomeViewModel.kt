package com.bangkit.coffee.presentation.home

import androidx.lifecycle.ViewModel
import com.bangkit.coffee.data.repository.DiseaseRepository
import com.bangkit.coffee.presentation.home.components.DetectionStep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    diseaseRepository: DiseaseRepository,
) : ViewModel() {

    private val _stateFlow = MutableStateFlow(
        HomeState(
            detectionSteps = DetectionStep.defaultList,
            diseases = diseaseRepository.getAll()
        )
    )
    val stateFlow = _stateFlow.asStateFlow()
}