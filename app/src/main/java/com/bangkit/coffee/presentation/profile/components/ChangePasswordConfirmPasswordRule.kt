package com.bangkit.coffee.presentation.profile.components

import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.annotations.FieldValidation
import me.naingaungluu.formconductor.validation.rules.StateBasedValidationRule

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@FieldValidation(
    fieldType = String::class,
    validator = ChangePasswordConfirmPasswordRule::class
)
annotation class ChangePasswordConfirmPassword

object ChangePasswordConfirmPasswordRule :
    StateBasedValidationRule<String, ChangePasswordConfirmPassword, ChangePasswordForm> {
    override fun validate(
        value: String,
        options: ChangePasswordConfirmPassword,
        formState: ChangePasswordForm
    ): FieldResult {
        return if (value == formState.newPassword) {
            FieldResult.Success
        } else {
            FieldResult.Error("Confirm password not match", this)
        }
    }

}