package com.bangkit.coffee.data.source.remote.model

data class VerifyCodeUser(
    val email: String?,
    val verificationCode: String
)
