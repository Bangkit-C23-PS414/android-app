package com.bangkit.coffee.data.repository

import android.content.Context
import com.bangkit.coffee.data.source.datastore.UserPreferences
import com.bangkit.coffee.data.source.datastore.UserPreferencesKeys
import com.bangkit.coffee.di.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
) : UserPreferencesRepository {

    override val flow: Flow<UserPreferences>
        get() = context.dataStore.data.map { pref ->
            UserPreferences(
                token = pref[UserPreferencesKeys.token] ?: "no-token",
                locale = pref[UserPreferencesKeys.locale] ?: "id-ID",
                theme = pref[UserPreferencesKeys.theme] ?: "default"
            )
        }

    override val tokenFlow: Flow<String>
        get() = context.dataStore.data.map { pref ->
            pref[UserPreferencesKeys.token] ?: "no-token"
        }

    override suspend fun updateToken(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteToken() {
        TODO("Not yet implemented")
    }
}