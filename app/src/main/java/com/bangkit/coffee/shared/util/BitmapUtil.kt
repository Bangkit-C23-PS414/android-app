package com.bangkit.coffee.shared.util

import android.graphics.Bitmap
import android.media.ThumbnailUtils
import java.io.File

fun Bitmap.cropCenter(size: Int) = cropCenter(size, size)
fun Bitmap.cropCenter(targetWidth: Int, targetHeight: Int): Bitmap {
    return ThumbnailUtils.extractThumbnail(this, targetWidth, targetHeight)
}

fun Bitmap.toFile(): File {
    val file = File.createTempFile("image", ".jpg")
    compress(Bitmap.CompressFormat.JPEG, 90, file.outputStream())
    return file
}