package com.bangkit.coffee.presentation.home

import androidx.lifecycle.ViewModel
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.DiseaseDummies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val detectionSteps = listOf(
        DetectionStep(R.drawable.detect_1, R.string.how_to_detect_step_1, 1),
        DetectionStep(R.drawable.detect_2, R.string.how_to_detect_step_2, 2),
        DetectionStep(R.drawable.detect_3, R.string.how_to_detect_step_3, 3),
    )

    private val diseases = DiseaseDummies

    private val _stateFlow = MutableStateFlow(
        HomeState(
            detectionSteps = detectionSteps,
            diseases = diseases
        )
    )
    val stateFlow = _stateFlow.asStateFlow()
}