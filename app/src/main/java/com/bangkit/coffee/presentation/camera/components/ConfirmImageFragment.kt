package com.bangkit.coffee.presentation.camera.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.bangkit.coffee.R
import com.bangkit.coffee.shared.theme.AppTheme

@Composable
fun ConfirmImageFragment(
    modifier: Modifier = Modifier,
    image: Uri = Uri.EMPTY
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            model = image,
            contentDescription = stringResource(R.string.image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .testTag("CameraConfirm")
        )
        Spacer(modifier = Modifier.weight(1f))

        ConfirmImageToolbar()
    }
}

@Preview(name = "ConfirmImageFragment", showBackground = true)
@Composable
private fun PreviewConfirmImageFragment() {
    AppTheme {
        ConfirmImageFragment()
    }
}