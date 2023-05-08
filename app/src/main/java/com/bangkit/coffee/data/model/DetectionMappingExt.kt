package com.bangkit.coffee.data.model

import com.bangkit.coffee.data.source.local.entity.DetectionEntity
import com.bangkit.coffee.data.source.network.response.DetectionResponse

/* Local <--> External Segment */

@JvmName("detection_ExternalToLocal")
fun List<Detection>.toLocal() = map(Detection::toLocal)
fun Detection.toLocal() = DetectionEntity(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

@JvmName("detection_LocalToExternal")
fun List<DetectionEntity>.toExternal() = map(DetectionEntity::toExternal)
fun DetectionEntity.toExternal() = Detection(
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
fun Detection.toRemote() = DetectionResponse(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

@JvmName("detection_RemoteToExternal")
fun List<DetectionResponse>.toExternal() = map(DetectionResponse::toExternal)
fun DetectionResponse.toExternal() = Detection(
    id = id,
    userId = userId,
    imageUrl = imageUrl,
    result = result,
    createdAt = createdAt,
    detectedAt = detectedAt,
)

/* Local <--> Remote Segment */

@JvmName("detection_LocalToRemote")
fun List<DetectionEntity>.toRemote() = map(DetectionEntity::toRemote)
fun DetectionEntity.toRemote() = toExternal().toRemote()

@JvmName("detection_RemoteToLocal")
fun List<DetectionResponse>.toLocal() = map(DetectionResponse::toLocal)
fun DetectionResponse.toLocal() = toExternal().toLocal()