package com.bangkit.coffee.presentation.camera

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.camera.components.CameraFragment
import com.bangkit.coffee.presentation.camera.components.ConfirmImageFragment
import com.bangkit.coffee.presentation.camera.components.RequestPermissionDialog
import com.bangkit.coffee.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    state: CameraState = CameraState(),
    actions: CameraActions = CameraActions()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            if (state.image == null) {
                                R.string.take_a_picture
                            } else {
                                R.string.confirm_image
                            }
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = actions.navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.close),
                        )
                    }
                }
            )
        },
    ) { contentPadding ->
        val cameraPermissionState = rememberPermissionState(
            Manifest.permission.CAMERA
        )

        if (cameraPermissionState.status.isGranted) {
            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { uri -> uri?.let { actions.setImage(it) } },
            )

            ProvideCameraActions(actions = actions) {
                if (state.image == null) {
                    CameraFragment(
                        modifier = Modifier.padding(contentPadding),
                        isFlashOn = state.isFlashOn,
                        isCapturing = state.isCapturing,
                        pickFromGallery = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                } else {
                    ConfirmImageFragment(
                        modifier = Modifier.padding(contentPadding),
                        image = state.image
                    )
                }
            }
        } else {
            RequestPermissionDialog(
                onConfirm = { cameraPermissionState.launchPermissionRequest() },
                onDismiss = actions.navigateUp
            )
        }
    }

    BackHandler(enabled = state.image != null) {
        actions.clearImage()
    }
}

@Composable
@Preview(name = "Camera")
private fun CameraScreenPreview() {
    AppTheme {
        CameraScreen()
    }
}

