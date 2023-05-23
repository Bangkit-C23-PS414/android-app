package com.bangkit.coffee.presentation.camera.components

import android.util.Rational
import android.view.MotionEvent
import android.view.Surface.ROTATION_0
import android.view.ViewGroup
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.ViewPort
import androidx.camera.view.PreviewView
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.bangkit.coffee.R
import com.bangkit.coffee.presentation.camera.CameraActions
import com.bangkit.coffee.presentation.camera.LocalCameraActions
import com.bangkit.coffee.presentation.camera.ProvideCameraActions
import com.bangkit.coffee.ui.KopintarAppActions
import com.bangkit.coffee.ui.LocalKopintarAppActions
import com.bangkit.coffee.ui.ProvideKopintarAppActions
import com.bangkit.coffee.ui.theme.AppTheme
import com.bangkit.coffee.util.executor
import com.bangkit.coffee.util.getCameraProvider
import com.bangkit.coffee.util.takePicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@Composable
fun CameraFragment(
    modifier: Modifier = Modifier,
    isFlashOn: Boolean = false,
    isCapturing: Boolean = false,
    pickFromGallery: () -> Unit = {},
) {
    val kopintarAppActions = LocalKopintarAppActions.current
    val context = LocalContext.current
    val actions = LocalCameraActions.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    var camera by remember { mutableStateOf<Camera?>(null) }
    val viewFinder = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }
    val previewUseCase = remember {
        androidx.camera.core.Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(ROTATION_0)
            .build()
    }
    val imageCaptureUseCase = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(ROTATION_0)
            .build()
    }

    var focusRingVisible by remember { mutableStateOf(false) }
    var focusRingOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    LaunchedEffect(previewUseCase, imageCaptureUseCase) {
        val cameraProvider = context.getCameraProvider()
        try {
            val viewPort = ViewPort.Builder(
                Rational(viewFinder.width, viewFinder.width),
                viewFinder.display.rotation
            ).setScaleType(ViewPort.FILL_CENTER).build()

            val useCaseGroup = UseCaseGroup.Builder()
                .setViewPort(viewPort)
                .addUseCase(previewUseCase)
                .addUseCase(imageCaptureUseCase)
                .build()

            // Must unbind the use-cases before rebinding them.
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                useCaseGroup
            )

            viewFinder.setOnTouchListener { view, event ->
                return@setOnTouchListener when (event.action) {
                    MotionEvent.ACTION_DOWN -> true
                    MotionEvent.ACTION_UP -> {
                        focusRingVisible = true
                        focusRingOffset = Offset(event.x, event.y)

                        val factory = viewFinder.meteringPointFactory
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

            previewUseCase.setSurfaceProvider(viewFinder.surfaceProvider)
        } catch (ex: Exception) {
            Timber.tag("CameraPreview").e(ex, "Use case binding failed")
        }
    }

    LaunchedEffect(isFlashOn) {
        camera?.let {
            if (it.cameraInfo.hasFlashUnit()) {
                it.cameraControl.enableTorch(isFlashOn)
            }
        }
    }

    LaunchedEffect(focusRingOffset) {
        delay(1000)
        focusRingVisible = false
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            Surface(
                modifier = Modifier
                    .aspectRatio(1f)
                    .align(Alignment.Center)
            ) {
                Box {
                    AndroidView(
                        factory = { viewFinder },
                    )

                    androidx.compose.animation.AnimatedVisibility(
                        visible = focusRingVisible,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Layout(
                            modifier = Modifier.align(Alignment.TopStart),
                            content = { FocusRing() },
                            measurePolicy = { measurables, constraints ->
                                val content = measurables[0].measure(constraints)
                                layout(constraints.maxWidth, constraints.maxHeight) {
                                    content.place(
                                        IntOffset(
                                            (focusRingOffset.x - content.width / 2).roundToInt(),
                                            (focusRingOffset.y - content.height / 2).roundToInt()
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }

            FocusOnLeafBadge(
                modifier = modifier.align(Alignment.BottomCenter)
            )
        }

        CameraToolbar(
            flashEnabled = camera?.cameraInfo?.hasFlashUnit() == true,
            isFlashOn = isFlashOn,
            isCapturing = isCapturing,
            pickFromGallery = pickFromGallery,
            onCapture = {
                coroutineScope.launch(Dispatchers.IO) {
                    actions.capture()
                    try {
                        val file = imageCaptureUseCase.takePicture(context.executor)
                        actions.setImage(file.toUri())
                    } catch (e: Exception) {
                        kopintarAppActions.showToast(context.resources.getString(R.string.capture_error))
                        actions.cancelCapture()
                    }
                }
            },
        )
    }
}

@Preview(name = "ShowCamera", showBackground = true)
@Composable
private fun PreviewCameraFragment() {
    AppTheme {
        ProvideKopintarAppActions(actions = KopintarAppActions()) {
            ProvideCameraActions(actions = CameraActions()) {
                CameraFragment()
            }
        }
    }
}