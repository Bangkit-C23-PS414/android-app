package com.bangkit.coffee.presentation.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.presentation.history.components.FilterHistoryBottomSheet
import com.bangkit.coffee.presentation.history.components.ImageDetectionCard
import com.bangkit.coffee.presentation.history.components.ImageDetectionGroupHeader
import com.bangkit.coffee.ui.theme.AppTheme
import com.bangkit.coffee.util.header
import java.util.Date
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    state: HistoryState = HistoryState(),
    actions: HistoryActions = HistoryActions()
) {
    val lazyGridState = rememberLazyGridState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.history)) },
                actions = {
                    IconButton(onClick = actions.toggleFilter) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = stringResource(R.string.filter_history)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = lazyGridState,
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(contentPadding)
        ) {
            header(key = "header-1") {
                ImageDetectionGroupHeader(
                    modifier = Modifier.padding(bottom = 4.dp),
                    date = Date()
                )
            }

            items(state.imageDetections, key = { it.id }) { imageDetection ->
                ImageDetectionCard(
                    imageDetection = imageDetection,
                    onClick = { actions.navigateToDetailImageDetection("42") }
                )
            }

            header(key = "header-2") {
                ImageDetectionGroupHeader(
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp),
                    date = Date()
                )
            }

            items(state.imageDetections, key = { "copy" + it.id }) { imageDetection ->
                ImageDetectionCard(
                    imageDetection = imageDetection,
                    onClick = { actions.navigateToDetailImageDetection("42") }
                )
            }
        }
    }

    if (state.filterVisible) {
        ProvideHistoryActions(actions = actions) {
            FilterHistoryBottomSheet(
                formData = state.filterFormData,
            )
        }
    }
}

@Composable
@Preview(name = "History", showBackground = true)
private fun HistoryScreenPreview() {
    AppTheme {
        HistoryScreen(
            state = HistoryState(
                imageDetections = List(6) {
                    ImageDetection(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        "https://picsum.photos/300",
                        "Cercospora Leaf Spot",
                        Date(),
                        Date()
                    )
                }
            )
        )
    }
}

