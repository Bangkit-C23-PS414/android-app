package com.bangkit.coffee.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.home.DetectionStep
import com.bangkit.coffee.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetectionStepCard(
    modifier: Modifier = Modifier,
    detectionStep: DetectionStep
) {
    Card(modifier = modifier) {
        Image(
            painter = painterResource(detectionStep.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        Badge(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
        ) {
            Text(text = stringResource(R.string.step_number, detectionStep.step))
        }
        Text(
            text = stringResource(detectionStep.description),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )
    }
}

@Preview(name = "DetectionStepCard", showBackground = true)
@Composable
private fun PreviewDetectionStepCard() {
    AppTheme {
        DetectionStepCard(
            detectionStep = DetectionStep(
                R.drawable.detect_1,
                R.string.how_to_detect_step_1,
                1
            )
        )
    }
}