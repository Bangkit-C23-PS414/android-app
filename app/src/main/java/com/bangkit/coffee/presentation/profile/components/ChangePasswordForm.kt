package com.bangkit.coffee.presentation.profile.components

import me.naingaungluu.formconductor.annotations.Form
import me.naingaungluu.formconductor.annotations.MinLength

@Form
data class ChangePasswordForm(
    @MinLength(6)
    val oldPassword: String = "",

    @MinLength(6)
    val newPassword: String = "",

    @MinLength(6)
    @ChangePasswordConfirmPassword
    val confirmNewPassword: String = "",
)
