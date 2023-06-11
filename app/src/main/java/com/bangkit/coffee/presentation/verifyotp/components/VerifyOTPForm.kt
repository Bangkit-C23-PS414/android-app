package com.bangkit.coffee.presentation.verifyotp.components

import me.naingaungluu.formconductor.annotations.MaxLength
import me.naingaungluu.formconductor.annotations.MinLength

data class VerifyOTPForm(
    @MinLength(6)
    @MaxLength(6)
    val code: String = "",
)
