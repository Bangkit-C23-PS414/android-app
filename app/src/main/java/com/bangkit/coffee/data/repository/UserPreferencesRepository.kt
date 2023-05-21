package com.bangkit.coffee.data.repository

import com.bangkit.coffee.data.source.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val flow: Flow<UserPreferences>
    val tokenFlow: Flow<String?>

    suspend fun updateToken(token: String)
    suspend fun deleteToken()
}