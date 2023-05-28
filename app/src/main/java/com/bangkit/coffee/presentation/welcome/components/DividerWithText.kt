package com.bangkit.coffee.presentation.welcome.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.ui.theme.AppTheme

@Composable
fun DividerWithText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier.weight(1f))

        Text(
            text = text,
            modifier = Modifier.padding(8.dp)
        )

        Divider(modifier = Modifier.weight(1f))
    }
}

@Preview(name = "DividerWithText", showBackground = true)
@Composable
private fun PreviewDividerWithText() {
    AppTheme {
        DividerWithText(
            modifier = Modifier.padding(24.dp),
            text = stringResource(id = R.string.or)
        )
    }
}