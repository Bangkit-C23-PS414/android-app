package com.bangkit.coffee.data

import com.bangkit.coffee.data.source.local.LocalDetection
import com.bangkit.coffee.data.source.remote.RemoteDetection

/* Local <--> External Segment */

@JvmName("detection_ExternalToLocal")
fun List<Detection>.toLocal() = map(Detection::toLocal)
fun Detection.toLocal() = LocalDetection(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

@JvmName("detection_LocalToExternal")
fun List<LocalDetection>.toExternal() = map(LocalDetection::toExternal)
fun LocalDetection.toExternal() = Detection(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

/* Remote <--> External Segment */

@JvmName("detection_ExternalToRemote")
fun List<Detection>.toRemote() = map(Detection::toRemote)
fun Detection.toRemote() = RemoteDetection(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

@JvmName("detection_RemoteToExternal")
fun List<RemoteDetection>.toExternal() = map(RemoteDetection::toExternal)
fun RemoteDetection.toExternal() = Detection(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

/* Local <--> Remote Segment */

@JvmName("detection_LocalToRemote")
fun List<LocalDetection>.toRemote() = map(LocalDetection::toRemote)
fun LocalDetection.toRemote() = toExternal().toRemote()

@JvmName("detection_RemoteToLocal")
fun List<RemoteDetection>.toLocal() = map(RemoteDetection::toLocal)
fun RemoteDetection.toLocal() = toExternal().toLocal()