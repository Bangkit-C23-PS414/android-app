package com.bangkit.coffee.domain.entity

data class DetectionResult(
    val id: String,
    val name: String,
    val disease: Disease?,
) {
    companion object {
        val list = listOf(
            DetectionResult("healthy", "Healthy", null),
            DetectionResult("disease-1", "Disease 1", null),
            DetectionResult("disease-2", "Disease 2", null),
            DetectionResult("disease-3", "Disease 3", null),
            DetectionResult("disease-4", "Disease 4", null),
        )
        val set = list.toSet()
    }
}