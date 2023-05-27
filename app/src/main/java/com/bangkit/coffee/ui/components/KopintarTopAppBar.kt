package com.bangkit.coffee.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.R
import com.bangkit.coffee.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KopintarTopAppBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.app_name)
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(title)
        },
    )
}

@Preview(name = "KopintarAppBar")
@Composable
private fun PreviewKopintarAppBar() {
    AppTheme {
        KopintarTopAppBar()
    }
}