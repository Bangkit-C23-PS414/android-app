package com.bangkit.coffee.presentation.resetpassword.components

import me.naingaungluu.formconductor.annotations.MinLength

data class ResetPasswordForm(
    @MinLength(8)
    val password: String = "",

    @ResetPasswordConfirmPassword
    val confirmPassword: String = "",
)
