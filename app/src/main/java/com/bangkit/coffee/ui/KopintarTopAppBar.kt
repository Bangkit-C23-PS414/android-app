package com.bangkit.coffee.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KopintarTopAppBar(
    modifier: Modifier = Modifier,
    appState: KopintarAppState = rememberKopintarAppState()
) {
    TopAppBar(
        modifier = modifier,
        title = {
            appState.getTitle?.let {
                Text(text = stringResource(it))
            }
        }
    )
}

@Preview(name = "KopintarAppBar")
@Composable
private fun PreviewKopintarAppBar() {
    KopintarTopAppBar()
}