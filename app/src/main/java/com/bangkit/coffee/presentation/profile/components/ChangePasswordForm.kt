package com.bangkit.coffee.presentation.profile.components

import me.naingaungluu.formconductor.annotations.Form
import me.naingaungluu.formconductor.annotations.MinLength

@Form
data class ChangePasswordForm(
    @MinLength(1)
    val oldPassword: String = "",

    @MinLength(1)
    val newPassword: String = "",

    @MinLength(1)
    val confirmNewPassword: String = "",
)
