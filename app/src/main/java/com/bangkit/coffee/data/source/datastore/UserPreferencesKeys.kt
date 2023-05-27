package com.bangkit.coffee.data.source.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesKeys {
    val token = stringPreferencesKey("token")
    val locale = stringPreferencesKey("locale")
    val theme = stringPreferencesKey("theme")
}