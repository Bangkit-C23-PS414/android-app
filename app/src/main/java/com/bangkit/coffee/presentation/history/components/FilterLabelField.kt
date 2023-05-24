package com.bangkit.coffee.presentation.history.components

import com.bangkit.coffee.domain.entity.DetectionResult
import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.annotations.FieldValidation
import me.naingaungluu.formconductor.validation.rules.StateBasedValidationRule

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@FieldValidation(
    fieldType = FilterLabelWrapper::class,
    validator = FilterLabelRule::class
)
annotation class FilterLabel

object FilterLabelRule :
    StateBasedValidationRule<FilterLabelWrapper, FilterLabel, FilterHistoryForm> {

    override fun validate(
        value: FilterLabelWrapper,
        options: FilterLabel,
        formState: FilterHistoryForm
    ): FieldResult {
        return if (value.value.isEmpty()) {
            FieldResult.Error("No filter selected", this)
        } else if (value.value.any { it !in DetectionResult.set }) {
            FieldResult.Error("Invalid filter item", this)
        } else {
            FieldResult.Success
        }
    }

}

data class FilterLabelWrapper(
    val value: Set<DetectionResult>,
) {
    fun update(checked: Boolean, detectionResult: DetectionResult): FilterLabelWrapper {
        return if (checked) plus(detectionResult)
        else minus(detectionResult)
    }

    fun plus(detectionResult: DetectionResult): FilterLabelWrapper = copy(
        value = value + detectionResult
    )

    fun minus(detectionResult: DetectionResult): FilterLabelWrapper = copy(
        value = value - detectionResult
    )
}

fun Set<DetectionResult>.wrap() = FilterLabelWrapper(this)

fun FilterLabelWrapper?.orEmpty() = this ?: DetectionResult.list.toSet().wrap()