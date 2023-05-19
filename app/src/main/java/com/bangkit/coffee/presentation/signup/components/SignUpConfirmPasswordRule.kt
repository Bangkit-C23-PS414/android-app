package com.bangkit.coffee.presentation.signup.components

import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.validation.rules.StateBasedValidationRule

object SignUpConfirmPasswordRule :
    StateBasedValidationRule<String, SignUpConfirmPassword, SignUpFormState> {
    override fun validate(
        value: String,
        options: SignUpConfirmPassword,
        formState: SignUpFormState
    ): FieldResult {
        return if (value == formState.password) {
            FieldResult.Success
        } else {
            FieldResult.Error("Confirm password not match", this)
        }
    }

}