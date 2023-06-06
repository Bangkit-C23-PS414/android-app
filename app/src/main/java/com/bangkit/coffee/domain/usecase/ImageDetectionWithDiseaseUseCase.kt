package com.bangkit.coffee.domain.usecase

import com.bangkit.coffee.data.repository.DiseaseRepository
import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.domain.entity.Disease
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.shared.const.LABEL_HEALTHY
import com.bangkit.coffee.shared.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDetectionWithDiseaseUseCase @Inject constructor(
    private val imageDetectionRepository: ImageDetectionRepository,
    private val diseaseRepository: DiseaseRepository
) {
    fun getStream(id: String): Flow<Pair<ImageDetection, Disease?>> {
        return imageDetectionRepository.getOne(id)
            .filterNotNull()
            .map { imageDetection ->
                if (imageDetection.isDetected && imageDetection.label != LABEL_HEALTHY) {
                    Pair(imageDetection, diseaseRepository.getOne(imageDetection.label))
                } else {
                    Pair(imageDetection, null)
                }
            }
    }

    suspend fun refreshOne(id: String): Resource<ImageDetection> {
        return imageDetectionRepository.refreshOne(id)
    }
}