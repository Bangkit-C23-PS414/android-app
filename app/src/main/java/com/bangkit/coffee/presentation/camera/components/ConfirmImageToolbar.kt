package com.bangkit.coffee.presentation.camera.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.minimumInteractiveComponentSize
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
fun ConfirmImageToolbar(
    modifier: Modifier = Modifier,
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
            onClick = actions.clearImage,
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.pick_image_from_gallery),
                modifier = Modifier.size(30.dp)
            )
        }

        if (false) {
            CircularProgressIndicator(
                modifier = Modifier.padding(10.dp)
            )
        } else {
            FloatingActionButton(
                onClick = {},
                shape = FloatingActionButtonDefaults.largeShape,
                elevation = FloatingActionButtonDefaults.zeroElevation(),
            ) {
                Icon(
                    imageVector = Icons.Filled.Upload,
                    contentDescription = stringResource(R.string.upload_image),
                    modifier = Modifier
                        .size(60.dp)
                        .padding(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.minimumInteractiveComponentSize())
    }
}

@Preview(name = "ConfirmImageToolbar", showBackground = true)
@Composable
private fun PreviewConfirmImageToolbar() {
    AppTheme {
        ProvideCameraActions(actions = CameraActions()) {
            ConfirmImageToolbar()
        }
    }
}