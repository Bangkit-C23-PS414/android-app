package com.bangkit.coffee.domain.entity

import com.bangkit.coffee.data.source.local.entity.LocalImageDetection
import com.bangkit.coffee.data.source.remote.response.RemoteImageDetection

/* Local <--> External Segment */

@JvmName("imageDetection_ExternalToLocal")
fun List<ImageDetection>.toLocal() = map(ImageDetection::toLocal)
fun ImageDetection.toLocal() = LocalImageDetection(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

@JvmName("imageDetection_LocalToExternal")
fun List<LocalImageDetection>.toExternal() = map(LocalImageDetection::toExternal)
fun LocalImageDetection.toExternal() = ImageDetection(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

/* Remote <--> External Segment */

@JvmName("imageDetection_ExternalToRemote")
fun List<ImageDetection>.toRemote() = map(ImageDetection::toRemote)
fun ImageDetection.toRemote() = RemoteImageDetection(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

@JvmName("imageDetection_RemoteToExternal")
fun List<RemoteImageDetection>.toExternal() = map(RemoteImageDetection::toExternal)
fun RemoteImageDetection.toExternal() = ImageDetection(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

/* Local <--> Remote Segment */

@JvmName("imageDetection_LocalToRemote")
fun List<LocalImageDetection>.toRemote() = map(LocalImageDetection::toRemote)
fun LocalImageDetection.toRemote() = toExternal().toRemote()

@JvmName("imageDetection_RemoteToLocal")
fun List<RemoteImageDetection>.toLocal() = map(RemoteImageDetection::toLocal)
fun RemoteImageDetection.toLocal() = toExternal().toLocal()