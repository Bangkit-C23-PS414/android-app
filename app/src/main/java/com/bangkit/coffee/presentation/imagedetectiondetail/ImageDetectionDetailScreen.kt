package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.imagedetectiondetail.components.DetailHealthyFragment
import com.bangkit.coffee.presentation.imagedetectiondetail.components.DetailSickFragment
import com.bangkit.coffee.ui.components.DisplayErrorFragment
import com.bangkit.coffee.ui.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetectionDetailScreen(
    state: ImageDetectionDetailState = ImageDetectionDetailState.Loading,
    actions: ImageDetectionDetailActions = ImageDetectionDetailActions()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.detection_result)) },
                navigationIcon = {
                    IconButton(onClick = actions.navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_up)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        @Suppress("DEPRECATION")
        SwipeRefresh(
            state = rememberSwipeRefreshState(
                isRefreshing = state is ImageDetectionDetailState.Loading
            ),
            onRefresh = actions.refresh
        ) {
            when (state) {
                ImageDetectionDetailState.Loading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(contentPadding)
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                ImageDetectionDetailState.Empty -> {
                    DisplayErrorFragment(
                        modifier = Modifier.padding(contentPadding)
                    )
                }

                is ImageDetectionDetailState.Healthy -> {
                    DetailHealthyFragment(
                        imageDetection = state.imageDetection,
                        modifier = Modifier.padding(contentPadding)
                    )
                }

                is ImageDetectionDetailState.Sick -> {
                    DetailSickFragment(
                        imageDetection = state.imageDetection,
                        disease = state.disease,
                        modifier = Modifier.padding(contentPadding)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(name = "ImageDetectionDetail", showBackground = true)
private fun ImageDetectionDetailScreenPreview() {
    AppTheme {
        ImageDetectionDetailScreen()
    }
}

