package com.bangkit.coffee.presentation.imagedetectiondetail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccuracyBadge(
    modifier: Modifier = Modifier,
    accuracy: Float
) {
    Badge(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.accuracy_format, accuracy),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(name = "AccuracyBadge")
@Composable
private fun PreviewAccuracyBadge() {
    AccuracyBadge(
        accuracy = 90f
    )
}