package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.domain.ImageDetectionDummy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ImageDetectionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val imageDetectionId = checkNotNull<String>(savedStateHandle["id"])

    private val _stateFlow = MutableStateFlow(ImageDetectionDetailState())
    val stateFlow = _stateFlow.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        _stateFlow.update {
            it.copy(
                loading = false,
                imageDetection = ImageDetectionDummy,
                disease = DiseaseDummy
            )
        }
    }

    fun refresh() {

    }
}