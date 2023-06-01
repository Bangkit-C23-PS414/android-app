package com.bangkit.coffee.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.domain.ImageDetectionDummies
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.presentation.history.components.FilterHistoryForm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val imageDetectionRepository: ImageDetectionRepository
) : ViewModel() {
    private val imageDetections: List<ImageDetection> = ImageDetectionDummies

    // UiState
    private val _stateFlow = MutableStateFlow(HistoryState(imageDetections = imageDetections))
    val stateFlow = _stateFlow.asStateFlow()

    // Pager
    @OptIn(ExperimentalCoroutinesApi::class)
    val pagerFlow = imageDetectionRepository.getPager()
        .mapLatest { pagingData -> pagingData.map { HistoryItem.Data(it) } }
        .mapLatest { pagingData ->
            pagingData.insertSeparators(TerminalSeparatorType.SOURCE_COMPLETE) { before, after ->
                when {
                    before == null -> HistoryItem.Separator(LocalDate.now(), true)
                    after == null -> null
                    shouldSeparate(before, after) -> HistoryItem.Separator(
                        after.value.createdAt.toLocalDate(),
                        false
                    )

                    else -> null
                }
            }
        }
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

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

    private fun shouldSeparate(
        before: HistoryItem.Data,
        after: HistoryItem.Data
    ): Boolean {
        return before.value.createdAt.toLocalDate() != after.value.createdAt.toLocalDate()
    }
}