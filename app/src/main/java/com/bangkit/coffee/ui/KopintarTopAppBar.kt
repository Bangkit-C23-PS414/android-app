package com.bangkit.coffee.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KopintarTopAppBar(
    modifier: Modifier = Modifier,
    topAppBarState: KopintarTopAppBarState = remember { KopintarTopAppBarState() }
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(topAppBarState.title)
        },
        actions = {
            topAppBarState.actions?.invoke(this)
        }
    )
}

@Preview(name = "KopintarAppBar")
@Composable
private fun PreviewKopintarAppBar() {
    var selected by remember { mutableStateOf(false) }

    AppTheme {
        KopintarTopAppBar(
            topAppBarState = KopintarTopAppBarState(
                title = "Hello",
                actions = {
                    IconButton(onClick = { selected = !selected }) {
                        Icon(
                            imageVector = if (selected) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Filled.FavoriteBorder
                            },
                            contentDescription = null
                        )
                    }
                }
            )
        )
    }
}