package com.bangkit.coffee.data.source.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesKeys {
    val token = stringPreferencesKey("token")
    val name = stringPreferencesKey("name")
    val email = stringPreferencesKey("email")
    val avatarUrl = stringPreferencesKey("avatarUrl")
    val blurHash = stringPreferencesKey("blurHash")
}