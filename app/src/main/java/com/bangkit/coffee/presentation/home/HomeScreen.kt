package com.bangkit.coffee.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.presentation.home.components.DetectionStepCard
import com.bangkit.coffee.presentation.home.components.DiseaseCard
import com.bangkit.coffee.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    state: HomeState = HomeState(),
    actions: HomeActions = HomeActions()
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
        ) {
            // Section 1
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.AutoFixHigh,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.how_to_detect),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                state.detectionSteps.forEach { detectionStep ->
                    DetectionStepCard(
                        modifier = Modifier.weight(1f),
                        detectionStep = detectionStep
                    )
                }
            }

            // Action button detect
            Button(
                onClick = actions.navigateToCamera,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Icon(
                    Icons.Filled.PhotoCamera,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.detect_now))
            }

            // Section 2
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.what_disease_detect),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            FlowRow(
                maxItemsInEachRow = 2,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top,
            ) {
                state.diseases.forEach { disease ->
                    DiseaseCard(
                        disease = disease,
                        modifier = Modifier
                            .weight(1f, true)
                            .padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(name = "Home", showBackground = true)
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            state = HomeState(
                detectionSteps = List(3) { step ->
                    DetectionStep(
                        R.drawable.detect_1,
                        R.string.how_to_detect_step_1,
                        step
                    )
                },
                diseases = List(6) { DiseaseDummy }
            )
        )
    }
}

