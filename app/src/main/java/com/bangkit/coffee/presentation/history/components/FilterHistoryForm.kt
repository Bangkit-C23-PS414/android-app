package com.bangkit.coffee.presentation.history.components

import com.bangkit.coffee.domain.entity.DetectionResult
import me.naingaungluu.formconductor.annotations.Form

@Form
data class FilterHistoryForm(
    val allTime: Boolean = true,

    @FilterDate
    val dateRange: FilterDateWrapper = FilterDateWrapper.empty,

    @FilterLabel
    val selectedLabel: FilterLabelWrapper = DetectionResult.list.toSet().wrap(),
)