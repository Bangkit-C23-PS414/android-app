package com.bangkit.coffee.shared.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.R
import com.bangkit.coffee.shared.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoffeeryTopAppBar(
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

@Preview(name = "RecoffeeryAppBar")
@Composable
private fun PreviewRecoffeeryAppBar() {
    AppTheme {
        RecoffeeryTopAppBar()
    }
}