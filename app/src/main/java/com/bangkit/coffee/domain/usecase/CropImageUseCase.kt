package com.bangkit.coffee.domain.usecase

import android.content.Context
import android.net.Uri
import com.bangkit.coffee.shared.util.cropCenter
import com.bangkit.coffee.shared.util.toBitmapWithExif
import com.bangkit.coffee.shared.util.toFile
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CropImageUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(uri: Uri): File {
        // Get file object
        val file = uri.toFile(context)
        // Read bitmap
        val bitmap = file.toBitmapWithExif()
        // Crop image
        val croppedBitmap = bitmap.cropCenter(500, 500)
        // Save image
        return croppedBitmap.toFile()
    }
}