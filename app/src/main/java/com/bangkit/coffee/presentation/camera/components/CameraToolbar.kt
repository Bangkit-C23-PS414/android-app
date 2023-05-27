package com.bangkit.coffee.presentation.camera.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.camera.CameraActions
import com.bangkit.coffee.presentation.camera.LocalCameraActions
import com.bangkit.coffee.presentation.camera.ProvideCameraActions
import com.bangkit.coffee.ui.theme.AppTheme
import com.bangkit.coffee.util.zeroElevation

@Composable
fun CameraToolbar(
    modifier: Modifier = Modifier,
    flashEnabled: Boolean = true,
    isFlashOn: Boolean = false,
    isCapturing: Boolean = false,
    onCapture: () -> Unit = {},
    pickFromGallery: () -> Unit = {},
) {
    val actions = LocalCameraActions.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = pickFromGallery,
        ) {
            Icon(
                imageVector = Icons.Filled.PhotoLibrary,
                contentDescription = stringResource(R.string.pick_image_from_gallery),
                modifier = Modifier.size(30.dp)
            )
        }

        if (isCapturing) {
            CircularProgressIndicator(
                modifier = Modifier.padding(10.dp)
            )
        } else {
            FloatingActionButton(
                onClick = onCapture,
                shape = FloatingActionButtonDefaults.largeShape,
                elevation = FloatingActionButtonDefaults.zeroElevation(),
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                Icon(
                    imageVector = Icons.Filled.Camera,
                    contentDescription = stringResource(R.string.capture),
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        IconToggleButton(
            enabled = flashEnabled,
            checked = isFlashOn,
            onCheckedChange = actions.toggleFlash,
        ) {
            Icon(
                imageVector = if (isFlashOn) {
                    Icons.Filled.FlashOn
                } else {
                    Icons.Filled.FlashOff
                },
                contentDescription = stringResource(R.string.toggle_flash),
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Preview(name = "CameraToolbar", showBackground = true)
@Composable
private fun PreviewCameraToolbar() {
    AppTheme {
        ProvideCameraActions(actions = CameraActions()) {
            CameraToolbar()
        }
    }
}