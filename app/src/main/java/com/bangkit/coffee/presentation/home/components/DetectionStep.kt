package com.bangkit.coffee.presentation.home.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bangkit.coffee.R

data class DetectionStep(
    @DrawableRes val image: Int,
    @StringRes val description: Int,
    val step: Int
) {
    companion object {
        val defaultList
            get() = listOf(
                DetectionStep(R.drawable.detect_1, R.string.how_to_detect_step_1, 1),
                DetectionStep(R.drawable.detect_2, R.string.how_to_detect_step_2, 2),
                DetectionStep(R.drawable.detect_3, R.string.how_to_detect_step_3, 3),
            )
    }
}