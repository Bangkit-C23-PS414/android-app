package com.bangkit.coffee.presentation.resetpassword.components

import me.naingaungluu.formconductor.FieldResult
import me.naingaungluu.formconductor.validation.rules.StateBasedValidationRule

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