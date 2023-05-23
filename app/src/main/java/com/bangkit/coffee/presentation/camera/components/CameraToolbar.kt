package com.bangkit.coffee.presentation.camera.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R

@Composable
fun CameraToolbar(
    modifier: Modifier = Modifier,
    flashEnabled: Boolean,
    isFlashOn: Boolean,
    toggleFlash: (Boolean) -> Unit,
    onCapture: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {},
        ) {
            Icon(
                imageVector = Icons.Filled.PhotoLibrary,
                contentDescription = stringResource(R.string.pick_image_from_gallery),
                modifier = Modifier.size(30.dp)
            )
        }
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { onCapture() }
        ) {
            Icon(
                imageVector = Icons.Filled.Camera,
                contentDescription = stringResource(R.string.capture),
                modifier = Modifier.size(60.dp)
            )
        }
        IconToggleButton(
            enabled = flashEnabled,
            checked = isFlashOn,
            onCheckedChange = toggleFlash,
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
    var isFlashOn by remember { mutableStateOf(false) }

    CameraToolbar(
        isFlashOn = isFlashOn,
        flashEnabled = true,
        toggleFlash = { value -> isFlashOn = value },
        onCapture = {}
    )
}