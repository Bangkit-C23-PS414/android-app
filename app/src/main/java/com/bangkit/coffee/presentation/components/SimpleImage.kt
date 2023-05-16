package com.bangkit.coffee.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bangkit.coffee.presentation.theme.AppTheme

@Composable
fun SimpleImage(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data("https://picsum.photos/1500")
                .crossfade(true)
                .diskCacheKey("key-3")
                .placeholderMemoryCacheKey("key-3")
                .build(),
            contentDescription = "Simple Image"
        )
    }
}

@Preview(name = "SimpleImage")
@Composable
private fun PreviewSimpleImage() {
    AppTheme {
        SimpleImage()
    }
}