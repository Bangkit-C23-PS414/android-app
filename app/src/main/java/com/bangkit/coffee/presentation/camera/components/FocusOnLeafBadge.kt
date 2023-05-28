package com.bangkit.coffee.presentation.camera.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.shared.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusOnLeafBadge(
    modifier: Modifier = Modifier
) {
    Badge(
        modifier = modifier.padding(vertical = 16.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
            imageVector = Icons.Filled.TipsAndUpdates,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(R.string.focus_on_leaf),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(name = "FocusOnLeafBadge", showBackground = true)
@Composable
private fun PreviewFocusOnLeafBadge() {
    AppTheme {
        FocusOnLeafBadge()
    }
}