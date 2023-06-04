package com.bangkit.coffee.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.presentation.history.components.FilterHistoryForm
import com.bangkit.coffee.shared.util.toEpochMilli
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val imageDetectionRepository: ImageDetectionRepository
) : ViewModel() {

    // UiState
    private val _stateFlow = MutableStateFlow(HistoryState())
    val stateFlow = _stateFlow.asStateFlow()

    // Pager
    @OptIn(ExperimentalCoroutinesApi::class)
    val pagerFlow = stateFlow
        .map { it.filterFormData }
        .distinctUntilChanged()
        .flatMapLatest { formData -> filterHistory(formData) }
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun filterHistory(formData: FilterHistoryForm): Flow<PagingData<HistoryItem>> {
        return imageDetectionRepository
            .getPager(
                labels = formData.selectedLabel.value.map { it.id },
                startDate = if (formData.allTime) null else formData.dateRange.startDate.toEpochMilli(),
                endDate = if (formData.allTime) null else formData.dateRange.endDate.toEpochMilli(),
            )
            .map { pagingData -> pagingData.map { HistoryItem.Data(it) } }
            .map { pagingData ->
                pagingData.insertSeparators(TerminalSeparatorType.SOURCE_COMPLETE) { before, after ->
                    if (before == null && after != null) {
                        HistoryItem.Separator(after.value.createdAt.toLocalDate(), true)
                    } else if (before != null && after != null && shouldSeparate(before, after)) {
                        HistoryItem.Separator(after.value.createdAt.toLocalDate(), false)
                    } else {
                        null
                    }
                }
            }
    }

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

    private fun shouldSeparate(
        before: HistoryItem.Data,
        after: HistoryItem.Data
    ): Boolean {
        return before.value.createdAt.toLocalDate() != after.value.createdAt.toLocalDate()
    }
}