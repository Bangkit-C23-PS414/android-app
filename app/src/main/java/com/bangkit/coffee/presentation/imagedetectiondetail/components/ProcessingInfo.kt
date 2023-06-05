package com.bangkit.coffee.presentation.imagedetectiondetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R

@Composable
fun ProcessingInfo(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Pending,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.still_processing),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.please_wait),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview(name = "ProcessingInfo")
@Composable
private fun PreviewProcessingInfo() {
    ProcessingInfo()
}