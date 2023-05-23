package com.bangkit.coffee.presentation.camera

import android.Manifest
import android.view.MotionEvent
import android.view.Surface
import android.view.ViewGroup
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.viewinterop.AndroidView
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.camera.components.CameraToolbar
import com.bangkit.coffee.presentation.camera.components.FocusOnLeafBadge
import com.bangkit.coffee.presentation.camera.components.FocusRing
import com.bangkit.coffee.presentation.camera.components.RequestPermissionDialog
import com.bangkit.coffee.util.executor
import com.bangkit.coffee.util.getCameraProvider
import com.bangkit.coffee.util.takePicture
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt
import androidx.camera.core.Preview as CameraPreview


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    state: CameraState = CameraState(), actions: CameraActions = CameraActions()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = stringResource(R.string.take_a_picture)) },
                navigationIcon = {
                    IconButton(onClick = actions.navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.close),
                        )
                    }
                })
        },
    ) { contentPadding ->
        val cameraPermissionState = rememberPermissionState(
            Manifest.permission.CAMERA
        )

        if (cameraPermissionState.status.isGranted) {
            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current
            val coroutineScope = rememberCoroutineScope()

            var camera by remember { mutableStateOf<Camera?>(null) }
            val previewView = remember {
                PreviewView(context).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
            val previewUseCase = remember { CameraPreview.Builder().build() }
            val imageCaptureUseCase = remember {
                ImageCapture.Builder().setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                    .setTargetRotation(Surface.ROTATION_0).build()
            }

            var focusRingVisible by remember { mutableStateOf(false) }
            var focusRingOffset by remember { mutableStateOf(Offset(0f, 0f)) }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .weight(1f)
                        .padding(contentPadding)
                ) {
                    LaunchedEffect(previewUseCase, imageCaptureUseCase) {
                        val cameraProvider = context.getCameraProvider()
                        try {
                            // Must unbind the use-cases before rebinding them.
                            cameraProvider.unbindAll()
                            camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                CameraSelector.DEFAULT_BACK_CAMERA,
                                previewUseCase,
                                imageCaptureUseCase
                            )

                            previewView.setOnTouchListener { view, event ->
                                return@setOnTouchListener when (event.action) {
                                    MotionEvent.ACTION_DOWN -> true
                                    MotionEvent.ACTION_UP -> {
                                        focusRingVisible = true
                                        focusRingOffset = Offset(event.x, event.y)

                                        val factory = previewView.meteringPointFactory
                                        val point = factory.createPoint(event.x, event.y)
                                        val action = FocusMeteringAction.Builder(point)
                                            .setAutoCancelDuration(3, TimeUnit.SECONDS)
                                            .build()
                                        camera?.cameraControl?.startFocusAndMetering(action)
                                        view.performClick()
                                    }

                                    else -> false
                                }
                            }

                            previewUseCase.setSurfaceProvider(previewView.surfaceProvider)
                        } catch (ex: Exception) {
                            Timber.tag("CameraPreview").e(ex, "Use case binding failed")
                        }
                    }

                    LaunchedEffect(state.isFlashOn) {
                        camera?.let {
                            if (it.cameraInfo.hasFlashUnit()) {
                                it.cameraControl.enableTorch(state.isFlashOn)
                            }
                        }
                    }

                    AndroidView(
                        factory = { previewView },
                        modifier = Modifier.fillMaxSize(),
                    )

                    FocusOnLeafBadge()

                    if(focusRingVisible) {
                        Layout(
                            modifier = Modifier.align(Alignment.TopStart),
                            content = {
                                FocusRing()
                            },
                            measurePolicy = { measurables, constraints ->
                                val content = measurables[0].measure(constraints)
                                layout(constraints.maxWidth, constraints.maxHeight) {
                                    content.placeRelative(
                                        IntOffset(
                                            (focusRingOffset.x - content.width / 2).roundToInt(),
                                            (focusRingOffset.y - content.height / 2).roundToInt()
                                        )
                                    )
                                }
                            }
                        )
                    }

                    LaunchedEffect(focusRingOffset) {
                        delay(1000)
                        focusRingVisible = false
                    }
                }

                CameraToolbar(flashEnabled = camera?.cameraInfo?.hasFlashUnit() == true,
                    isFlashOn = state.isFlashOn,
                    toggleFlash = actions.toggleFlash,
                    onCapture = {
                        coroutineScope.launch(Dispatchers.IO) {
                            imageCaptureUseCase.takePicture(context.executor).let { file ->
                                Timber.d("File: ${file.name}")
                            }
                        }
                    })
            }
        } else {
            RequestPermissionDialog(
                onConfirm = { cameraPermissionState.launchPermissionRequest() },
                onDismiss = actions.navigateUp
            )
        }
    }
}

@Composable
@Preview(name = "Camera")
private fun CameraScreenPreview() {
    CameraScreen()
}

