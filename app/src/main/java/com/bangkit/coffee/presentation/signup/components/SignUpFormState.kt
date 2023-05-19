package com.bangkit.coffee.presentation.signup.components

import me.naingaungluu.formconductor.annotations.EmailAddress
import me.naingaungluu.formconductor.annotations.IsChecked
import me.naingaungluu.formconductor.annotations.MinLength

data class SignUpFormState(
    @MinLength(1)
    val name: String = "",

    @EmailAddress
    val email: String = "",

    @MinLength(6)
    val password: String = "",

    @SignUpConfirmPassword
    val confirmPassword: String = "",

    @IsChecked
    val termsAndConditionAgreed: Boolean = false
)
