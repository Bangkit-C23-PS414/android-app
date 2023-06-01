package com.bangkit.coffee.data.source.remote.model

import java.util.Date

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val token: String?,
    val tokenIssuedAt: Date?,
    val createdAt: Date
)
