package com.bangkit.coffee.presentation.forgotpassword.components

import me.naingaungluu.formconductor.annotations.EmailAddress

data class ForgotPasswordForm(
    @EmailAddress
    val email: String = "",
)