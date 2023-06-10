package com.bangkit.coffee.presentation.profile.components

import me.naingaungluu.formconductor.annotations.Form
import me.naingaungluu.formconductor.annotations.MinLength

@Form
data class ChangePasswordForm(
    @MinLength(8)
    val oldPassword: String = "",

    @MinLength(8)
    val newPassword: String = "",

    @MinLength(8)
    @ChangePasswordConfirmPassword
    val confirmNewPassword: String = "",
)
