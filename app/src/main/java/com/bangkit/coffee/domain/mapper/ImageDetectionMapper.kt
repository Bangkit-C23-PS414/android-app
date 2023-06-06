package com.bangkit.coffee.domain.mapper

import com.bangkit.coffee.data.source.local.entity.LocalImageDetection
import com.bangkit.coffee.data.source.remote.model.RemoteImageDetection
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.shared.util.toEpochMilli
import com.bangkit.coffee.shared.util.toLocalDateTime
import java.time.LocalDateTime

/* Local <--> External Segment */

@JvmName("imageDetection_ExternalToLocal")
fun List<ImageDetection>.toLocal() = map(ImageDetection::toLocal)
fun ImageDetection.toLocal() = LocalImageDetection(
    id = id,
    fileURL = fileURL,
    blurHash = blurHash,
    label = label,
    confidence = confidence,
    isDetected = isDetected,
    inferenceTime = inferenceTime,
    createdAt = createdAt,
    detectedAt = detectedAt,
    syncAt = LocalDateTime.now(),
)

@JvmName("imageDetection_LocalToExternal")
fun List<LocalImageDetection>.toExternal() = map(LocalImageDetection::toExternal)
fun LocalImageDetection.toExternal() = ImageDetection(
    id = id,
    fileURL = fileURL,
    blurHash = blurHash,
    label = label,
    confidence = confidence,
    isDetected = isDetected,
    inferenceTime = inferenceTime,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

/* Remote <--> External Segment */

@JvmName("imageDetection_ExternalToRemote")
fun List<ImageDetection>.toRemote() = map(ImageDetection::toRemote)
fun ImageDetection.toRemote() = RemoteImageDetection(
    id = id,
    fileURL = fileURL,
    blurHash = blurHash,
    label = label,
    confidence = confidence,
    isDetected = isDetected,
    inferenceTime = inferenceTime,
    createdAt = createdAt.toEpochMilli(),
    detectedAt = detectedAt.toEpochMilli(),
)

@JvmName("imageDetection_RemoteToExternal")
fun List<RemoteImageDetection>.toExternal() = map(RemoteImageDetection::toExternal)
fun RemoteImageDetection.toExternal() = ImageDetection(
    id = id,
    fileURL = fileURL,
    blurHash = blurHash,
    label = label,
    confidence = confidence,
    isDetected = isDetected,
    inferenceTime = inferenceTime,
    createdAt = createdAt.toLocalDateTime(),
    detectedAt = detectedAt.toLocalDateTime(),
)

/* Local <--> Remote Segment */

@JvmName("imageDetection_LocalToRemote")
fun List<LocalImageDetection>.toRemote() = map(LocalImageDetection::toRemote)
fun LocalImageDetection.toRemote() = toExternal().toRemote()

@JvmName("imageDetection_RemoteToLocal")
fun List<RemoteImageDetection>.toLocal() = map(RemoteImageDetection::toLocal)
fun RemoteImageDetection.toLocal() = toExternal().toLocal()