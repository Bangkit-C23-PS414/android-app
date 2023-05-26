package com.bangkit.coffee.presentation.diseasedetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.bangkit.coffee.domain.entity.Disease


/**
 * UI State that represents DiseaseDetailScreen
 **/
sealed class DiseaseDetailState {
    object Loading : DiseaseDetailState()

    data class Success(
        val disease: Disease,
    ) : DiseaseDetailState()
}

/**
 * DiseaseDetail Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class DiseaseDetailActions(
    val navigateUp: () -> Unit = {}
)

/**
 * Compose Utility to retrieve actions from nested components
 **/
val LocalDiseaseDetailActions = staticCompositionLocalOf<DiseaseDetailActions> {
    error("{NAME} Actions Were not provided, make sure ProvideDiseaseDetailActions is called")
}

@Composable
fun ProvideDiseaseDetailActions(actions: DiseaseDetailActions, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDiseaseDetailActions provides actions) {
        content.invoke()
    }
}

