package com.bangkit.coffee.presentation.camera.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.bangkit.coffee.ml.CoffeeModel
import com.bangkit.coffee.presentation.camera.LocalClassifierResult
import com.bangkit.coffee.shared.util.YuvToRgbConverter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.model.Model
import timber.log.Timber
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class CoffeeLeafClassifier(
    context: Context,
    private val listener: DetectorListener
) : ImageAnalysis.Analyzer {

    private val model: CoffeeModel by lazy {
        val compatList = CompatibilityList()

        val options = if (compatList.isDelegateSupportedOnThisDevice) {
            Timber.d("This device is GPU Compatible ")
            Model.Options.Builder().setDevice(Model.Device.GPU).build()
        } else {
            Timber.d("This device is GPU Incompatible ")
            Model.Options.Builder().setNumThreads(4).build()
        }

        CoffeeModel.newInstance(context, options)
    }

    @OptIn(ExperimentalTime::class)
    override fun analyze(image: ImageProxy) {
        try {
            val (outputs, elapsed) = measureTimedValue {
                val tfImage = TensorImage.fromBitmap(toBitmap(image))
                model.process(tfImage)
            }

            val result = outputs.probabilityAsCategoryList
                .apply { sortByDescending { it.score } }
                .first()

            listener.onResult(
                LocalClassifierResult(result.label, result.score, elapsed.inWholeMilliseconds)
            )
        } catch (e: Exception) {
            listener.onError(e.localizedMessage ?: "Analyzer camera error")
        } finally {
            image.close()
        }
    }

    /**
     * Convert Image Proxy to Bitmap
     */
    private val yuvToRgbConverter = YuvToRgbConverter(context)
    private lateinit var bitmapBuffer: Bitmap
    private lateinit var rotationMatrix: Matrix

    private fun toBitmap(imageProxy: ImageProxy): Bitmap? {
        @ExperimentalGetImage
        val image = imageProxy.image ?: return null

        // Initialise Buffer
        if (!::bitmapBuffer.isInitialized) {
            // The image rotation and RGB image buffer are initialized only once
            Timber.d("Initalise toBitmap()")
            rotationMatrix = Matrix()
            rotationMatrix.postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
            bitmapBuffer = Bitmap.createBitmap(
                imageProxy.width, imageProxy.height, Bitmap.Config.ARGB_8888
            )
        }

        // Pass image to an image analyser
        yuvToRgbConverter.yuvToRgb(image, bitmapBuffer)

        // Create the Bitmap in the correct orientation
        return Bitmap.createBitmap(
            bitmapBuffer,
            0,
            0,
            bitmapBuffer.width,
            bitmapBuffer.height,
            rotationMatrix,
            false
        )
    }

    interface DetectorListener {
        fun onError(error: String)
        fun onResult(result: LocalClassifierResult)
    }
}