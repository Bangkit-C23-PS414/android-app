package com.bangkit.coffee.presentation.camera.components

import android.util.Rational
import android.util.Size
import android.view.MotionEvent
import android.view.Surface.ROTATION_0
import android.view.ViewGroup
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.ViewPort
import androidx.camera.view.PreviewView
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.bangkit.coffee.R
import com.bangkit.coffee.app.LocalRecoffeeryAppActions
import com.bangkit.coffee.app.ProvideRecoffeeryAppActions
import com.bangkit.coffee.app.RecoffeeryAppActions
import com.bangkit.coffee.presentation.camera.CameraActions
import com.bangkit.coffee.presentation.camera.LocalCameraActions
import com.bangkit.coffee.presentation.camera.LocalClassifierResult
import com.bangkit.coffee.presentation.camera.ProvideCameraActions
import com.bangkit.coffee.shared.theme.AppTheme
import com.bangkit.coffee.shared.util.executor
import com.bangkit.coffee.shared.util.getCameraProvider
import com.bangkit.coffee.shared.util.takePicture
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
    useLocalClassifier: Boolean = false,
    localClassifierResult: LocalClassifierResult? = null,
    pickFromGallery: () -> Unit = {},
) {
    val appActions = LocalRecoffeeryAppActions.current
    val context = LocalContext.current
    val actions = LocalCameraActions.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    // TFLite
    val classifier = remember(useLocalClassifier) {
        if (useLocalClassifier) {
            CoffeeLeafClassifier(context, object : CoffeeLeafClassifier.DetectorListener {
                override fun onError(error: String) {
                    Timber.e(error)
                }

                override fun onResult(result: LocalClassifierResult) {
                    actions.setLocalClassifierResult(result)
                }
            })
        } else {
            null
        }
    }

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
    val imageAnalysisUseCase = remember(useLocalClassifier) {
        if (useLocalClassifier && classifier != null) {
            ImageAnalysis.Builder()
                .setTargetResolution(Size(224, 224))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                .build()
                .also { it.setAnalyzer(context.executor, classifier) }
        } else {
            null
        }
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

    LaunchedEffect(previewUseCase, imageCaptureUseCase, imageAnalysisUseCase) {
        val cameraProvider = context.getCameraProvider()
        try {
            val viewPort = ViewPort.Builder(
                Rational(viewFinder.width, viewFinder.width),
                viewFinder.display.rotation
            ).setScaleType(ViewPort.FILL_CENTER).build()

            val useCaseGroup = UseCaseGroup.Builder()
                .setViewPort(viewPort)
                .addUseCase(previewUseCase)
                .also { builder ->
                    imageAnalysisUseCase?.let {
                        builder.addUseCase(it)
                    }
                }
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
                        modifier = Modifier.testTag("CameraPreview")
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
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }

        if (useLocalClassifier && localClassifierResult != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = localClassifierResult.label,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                )
                Text(
                    text = stringResource(
                        R.string.percentage_template,
                        localClassifierResult.confidence * 100
                    ),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(
                    R.string.inference_time,
                    localClassifierResult.inferenceTime
                ),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        CameraToolbar(
            flashEnabled = camera?.cameraInfo?.hasFlashUnit() == true,
            isFlashOn = isFlashOn,
            isCapturing = isCapturing,
            pickFromGallery = pickFromGallery,
            onCapture = {
                coroutineScope.launch(Dispatchers.IO) {
                    actions.capturing()
                    try {
                        val file = imageCaptureUseCase.takePicture(context.executor)
                        actions.setImage(file.toUri())
                    } catch (e: Exception) {
                        appActions.showToast(context.resources.getString(R.string.capture_error))
                        actions.cancelCapturing()
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
        ProvideRecoffeeryAppActions(actions = RecoffeeryAppActions()) {
            ProvideCameraActions(actions = CameraActions()) {
                CameraFragment()
            }
        }
    }
}