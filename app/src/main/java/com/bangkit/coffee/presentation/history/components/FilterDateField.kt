package com.bangkit.coffee.presentation.history.components

import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.annotations.FieldValidation
import me.naingaungluu.formconductor.validation.rules.StateBasedValidationRule
import java.time.LocalDateTime

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@FieldValidation(
    fieldType = FilterDateWrapper::class,
    validator = FilterDateRule::class
)
annotation class FilterDate

object FilterDateRule :
    StateBasedValidationRule<FilterDateWrapper, FilterDate, FilterHistoryForm> {

    override fun validate(
        value: FilterDateWrapper,
        options: FilterDate,
        formState: FilterHistoryForm
    ): FieldResult {
        return if (formState.allTime) {
            FieldResult.Success
        } else if (value.startDate.isAfter(value.endDate)) {
            FieldResult.Error("Date range is invalid", this)
        } else {
            FieldResult.Success
        }
    }

}

data class FilterDateWrapper(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
) {
    companion object {
        val empty = FilterDateWrapper(
            startDate = LocalDateTime.now(),
            endDate = LocalDateTime.now()
        )
    }
}