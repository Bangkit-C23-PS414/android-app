package com.bangkit.coffee.data.source.datastore

data class UserPreferences(
    val token: String,
    val locale: String,
    val theme: String,
)