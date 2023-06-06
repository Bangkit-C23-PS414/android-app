package com.bangkit.coffee.presentation.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.ImageDetectionDummy
import com.bangkit.coffee.presentation.history.components.FilterHistoryBottomSheet
import com.bangkit.coffee.presentation.history.components.ImageDetectionCard
import com.bangkit.coffee.presentation.history.components.ImageDetectionGroupHeader
import com.bangkit.coffee.shared.components.DisplayErrorFragment
import com.bangkit.coffee.shared.components.pullrefresh.PullRefreshIndicator
import com.bangkit.coffee.shared.components.pullrefresh.pullRefresh
import com.bangkit.coffee.shared.components.pullrefresh.rememberPullRefreshState
import com.bangkit.coffee.shared.theme.AppTheme
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HistoryScreen(
    state: HistoryState = HistoryState(),
    actions: HistoryActions = HistoryActions(),
    pagerState: LazyPagingItems<HistoryItem>
) {
    val lazyGridState = rememberLazyGridState()

    val pullRefreshState = rememberPullRefreshState(
        pagerState.loadState.refresh is LoadState.Loading,
        { pagerState.refresh() },
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.history)) },
                actions = {
                    IconButton(
                        onClick = actions.toggleFilter,
                        modifier = Modifier.testTag("FilterButton")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FilterList,
                            contentDescription = stringResource(R.string.filter_history)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .pullRefresh(pullRefreshState)
                .fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = lazyGridState,
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // Display list
                items(
                    count = pagerState.itemCount,
                    key = pagerState.itemKey { it.key },
                    contentType = pagerState.itemContentType { it.type },
                    span = { index ->
                        when (pagerState[index]) {
                            is HistoryItem.Separator -> GridItemSpan(maxLineSpan)
                            else -> GridItemSpan(1)
                        }
                    }
                ) { index ->
                    when (val item = pagerState[index]) {
                        is HistoryItem.Data -> {
                            ImageDetectionCard(
                                imageDetection = item.value,
                                onClick = { actions.navigateToDetailImageDetection(item.value.id) },
                                modifier = Modifier
                                    .animateItemPlacement()
                                    .testTag("ImageDetectionCard")
                            )
                        }

                        is HistoryItem.Separator -> {
                            ImageDetectionGroupHeader(
                                date = item.date,
                                modifier = Modifier
                                    .padding(bottom = 4.dp, top = if (item.first) 0.dp else 8.dp)
                                    .animateItemPlacement()
                            )
                        }

                        else -> {}
                    }
                }


                // Show loading state for append
                when (pagerState.loadState.append) {
                    LoadState.Loading -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is LoadState.Error -> {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            ) {
                                Text(
                                    text = stringResource(R.string.cannot_load_more_data),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                                FilledTonalButton(onClick = { pagerState.retry() }) {
                                    Text(text = stringResource(R.string.retry))
                                }
                            }
                        }
                    }

                    is LoadState.NotLoading -> {}
                }
            }

            if (pagerState.loadState.refresh is LoadState.Error) {
                DisplayErrorFragment(message = stringResource(R.string.error_to_load_data_pull_to_try_again))
            }

            // If empty
            if (pagerState.loadState.append.endOfPaginationReached && pagerState.itemCount == 0) {
                DisplayErrorFragment(message = stringResource(R.string.history_empty))
            }

            PullRefreshIndicator(
                pagerState.loadState.refresh is LoadState.Loading,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }

    if (state.filterVisible) {
        ProvideHistoryActions(actions = actions) {
            FilterHistoryBottomSheet(
                formData = state.filterFormData,
                modifier = Modifier.testTag("FilterHistoryBottomSheet"),
            )
        }
    }
}

@Composable
@Preview(name = "History", showBackground = true)
private fun HistoryScreenPreview() {
    AppTheme {
        HistoryScreen(
            state = HistoryState(),
            pagerState = flowOf(
                PagingData.from(List<HistoryItem>(6) {
                    HistoryItem.Data(ImageDetectionDummy)
                })
            ).collectAsLazyPagingItems()
        )
    }
}

