package com.bangkit.coffee.data.source.remote.model

data class RegisterUser(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
