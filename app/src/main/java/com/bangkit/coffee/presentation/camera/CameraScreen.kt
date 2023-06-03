package com.bangkit.coffee.presentation.camera

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.camera.components.CameraFragment
import com.bangkit.coffee.presentation.camera.components.ConfirmImageFragment
import com.bangkit.coffee.presentation.camera.components.RequestPermissionDialog
import com.bangkit.coffee.shared.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    state: CameraState = CameraState(),
    actions: CameraActions = CameraActions()
) {
    val context = LocalContext.current

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
                    IconButton(
                        onClick = actions.navigateUp,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.close),
                        )
                    }
                },
                actions = {
                    if (state.image == null) {
                        val contentDescriptionText = stringResource(R.string.use_local_preview)

                        Switch(
                            modifier = Modifier
                                .semantics { contentDescription = contentDescriptionText }
                                .padding(end = 16.dp),
                            checked = state.useLocalClassifier,
                            onCheckedChange = actions.toggleLocalClassifier
                        )
                    }
                }
            )
        },
    ) { contentPadding ->
        val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

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
                        useLocalClassifier = state.useLocalClassifier,
                        localClassifierResult = state.localClassifierResult,
                        pickFromGallery = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                } else {
                    ConfirmImageFragment(
                        modifier = Modifier.padding(contentPadding),
                        image = state.image,
                        inProgress = state.inProgress
                    )
                }
            }
        } else {
            if (cameraPermissionState.status.shouldShowRationale) {
                RequestPermissionDialog(
                    onConfirm = { cameraPermissionState.launchPermissionRequest() },
                    onDismiss = actions.navigateUp
                )
            } else {
                LaunchedEffect(Unit) {
                    cameraPermissionState.launchPermissionRequest()
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(36.dp)
            ) {
                Text(
                    text = stringResource(R.string.camera_denied_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Text(
                    text = stringResource(R.string.camera_denied_description),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Button(onClick = {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }) {
                    Text(text = stringResource(R.string.open_settings))
                }
            }
        }
    }

    BackHandler(enabled = state.image != null) {
        actions.clearImage()
    }
}

@Composable
@Preview(name = "Camera", showBackground = true)
private fun CameraScreenPreview() {
    AppTheme {
        CameraScreen()
    }
}