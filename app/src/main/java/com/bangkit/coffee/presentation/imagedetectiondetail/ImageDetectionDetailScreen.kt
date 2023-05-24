package com.bangkit.coffee.presentation.imagedetectiondetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.imagedetectiondetail.components.DetailHealthyFragment
import com.bangkit.coffee.ui.components.DisplayErrorFragment
import com.bangkit.coffee.ui.components.LoadingContent

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
        LoadingContent(
            modifier = Modifier.padding(contentPadding),
            loading = state is ImageDetectionDetailState.Loading,
            empty = state is ImageDetectionDetailState.Empty,
            emptyContent = {
                DisplayErrorFragment()
            },
            onRefresh = actions.refresh
        ) {
            if (state is ImageDetectionDetailState.Sick) {
                DetailHealthyFragment(
                    imageDetection = state.imageDetection,
                    //disease = state.disease
                )
            }
        }
    }
}

@Composable
@Preview(name = "ImageDetectionDetail")
private fun ImageDetectionDetailScreenPreview() {
    ImageDetectionDetailScreen()
}

