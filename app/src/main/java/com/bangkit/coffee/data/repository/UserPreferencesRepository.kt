package com.bangkit.coffee.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.bangkit.coffee.data.source.datastore.UserPreferences
import com.bangkit.coffee.data.source.datastore.UserPreferencesKeys
import com.bangkit.coffee.di.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

    val flow: Flow<UserPreferences>
        get() = context.dataStore.data.map { pref ->
            UserPreferences(
                token = pref[UserPreferencesKeys.token],
                locale = pref[UserPreferencesKeys.locale] ?: "id-ID",
                theme = pref[UserPreferencesKeys.theme] ?: "default"
            )
        }

    val tokenFlow: Flow<String?>
        get() = context.dataStore.data.map { pref ->
            pref[UserPreferencesKeys.token]
        }

    suspend fun updateToken(token: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.token] = token
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { pref ->
            pref.remove(UserPreferencesKeys.token)
        }
    }
}