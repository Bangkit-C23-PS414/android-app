package com.bangkit.coffee.shared.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import java.io.File
import java.io.IOException

fun Uri.toFile(context: Context): File {
    return when (this.scheme) {
        ContentResolver.SCHEME_FILE -> this.toFile()
        ContentResolver.SCHEME_CONTENT -> {
            // Decide file
            val contentResolver: ContentResolver = context.contentResolver
            val tempFile = File.createTempFile("image", "leaf")
            val inputStream = contentResolver.openInputStream(this) ?: throw IOException()

            // Read and write
            inputStream.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            return tempFile
        }

        else -> throw IOException()
    }
}