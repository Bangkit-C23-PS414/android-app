package com.bangkit.coffee.presentation.signin.components

import me.naingaungluu.formconductor.annotations.EmailAddress
import me.naingaungluu.formconductor.annotations.Form
import me.naingaungluu.formconductor.annotations.MinLength

@Form
data class SignInForm(
    @EmailAddress
    val email: String = "",

    @MinLength(6)
    val password: String = "",
)