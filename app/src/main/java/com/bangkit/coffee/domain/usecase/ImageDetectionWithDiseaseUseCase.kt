package com.bangkit.coffee.domain.usecase

import com.bangkit.coffee.data.repository.ImageDetectionRepository
import com.bangkit.coffee.domain.DiseaseDummy
import com.bangkit.coffee.domain.entity.Disease
import com.bangkit.coffee.domain.entity.ImageDetection
import com.bangkit.coffee.shared.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDetectionWithDiseaseUseCase @Inject constructor(
    private val imageDetectionRepository: ImageDetectionRepository,
) {
    fun getStream(id: String): Flow<Pair<ImageDetection?, Disease?>> {
        return imageDetectionRepository.getOne(id).map { imageDetection ->
            Pair(imageDetection, DiseaseDummy)
        }
    }

    suspend fun refreshOne(id: String): Resource<ImageDetection> {
        return imageDetectionRepository.refreshOne(id)
    }
}