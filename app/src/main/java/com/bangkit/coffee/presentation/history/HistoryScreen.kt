package com.bangkit.coffee.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.presentation.components.SimpleScreen

@Composable
fun HistoryScreen(
    state: HistoryState = HistoryState(),
    actions: HistoryActions = HistoryActions()
) {
    SimpleScreen(
        text = "History",
        action = actions.navigateToDetail
    )
}

@Composable
@Preview(name = "History")
private fun HistoryScreenPreview() {
    HistoryScreen()
}

