package com.bangkit.coffee.presentation.signup.components

import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.annotations.FieldValidation
import me.naingaungluu.formconductor.validation.rules.StateBasedValidationRule

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@FieldValidation(
    fieldType = String::class,
    validator = SignUpConfirmPasswordRule::class
)
annotation class SignUpConfirmPassword

object SignUpConfirmPasswordRule :
    StateBasedValidationRule<String, SignUpConfirmPassword, SignUpForm> {
    override fun validate(
        value: String,
        options: SignUpConfirmPassword,
        formState: SignUpForm
    ): FieldResult {
        return if (value == formState.password) {
            FieldResult.Success
        } else {
            FieldResult.Error("Confirm password not match", this)
        }
    }

}