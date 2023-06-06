package com.bangkit.coffee.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.presentation.history.components.FilterHistoryForm
import java.time.LocalDate


/**
 * UI State that represents HistoryScreen
 **/
data class HistoryState(
    val filterVisible: Boolean = false,
    val filterFormData: FilterHistoryForm = FilterHistoryForm(),
)

/**
 * UI Model Holder that support separator
 */
sealed class HistoryItem(val key: String, val type: String) {
    class Data(val value: ImageDetection) : HistoryItem(value.id, "data")
    class Separator(
        val date: LocalDate,
        val first: Boolean
    ) : HistoryItem(date.toEpochDay().toString(), "separator")
}

/**
 * History Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class HistoryActions(
    val navigateToDetailImageDetection: (String) -> Unit = {},
    val toggleFilter: () -> Unit = {},
    val applyFilter: (FilterHistoryForm) -> Unit = {},
    val resetFilter: () -> Unit = {},
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalHistoryActions = staticCompositionLocalOf<HistoryActions> {
    error("{NAME} Actions Were not provided, make sure ProvideHistoryActions is called")
}

@Composable
fun ProvideHistoryActions(actions: HistoryActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalHistoryActions provides actions) {
        content.invoke()
    }
}

