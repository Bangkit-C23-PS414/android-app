package com.bangkit.coffee.presentation.resetpassword.components

import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.annotations.FieldValidation
import me.naingaungluu.formconductor.validation.rules.StateBasedValidationRule


@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@FieldValidation(
    fieldType = String::class,
    validator = ResetPasswordConfirmPasswordRule::class
)
annotation class ResetPasswordConfirmPassword

object ResetPasswordConfirmPasswordRule :
    StateBasedValidationRule<String, ResetPasswordConfirmPassword, ResetPasswordForm> {
    override fun validate(
        value: String,
        options: ResetPasswordConfirmPassword,
        formState: ResetPasswordForm
    ): FieldResult {
        return if (value == formState.password) {
            FieldResult.Success
        } else {
            FieldResult.Error("Confirm password not match", this)
        }
    }

}