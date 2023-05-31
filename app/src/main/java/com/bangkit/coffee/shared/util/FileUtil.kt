package com.bangkit.coffee.shared.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import java.io.File


fun File.toBitmapWithExif(): Bitmap {
    val bitmap = BitmapFactory.decodeFile(path)
    val exif = ExifInterface(path)
    val orientation = exif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )
    val rotate = when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_270 -> 270f
        ExifInterface.ORIENTATION_ROTATE_180 -> 180f
        ExifInterface.ORIENTATION_ROTATE_90 -> 90f
        else -> 0f
    }
    val matrix = Matrix().apply { postRotate(rotate) }
    return Bitmap.createBitmap(
        bitmap, 0, 0,
        bitmap.width, bitmap.height,
        matrix, true
    )
}