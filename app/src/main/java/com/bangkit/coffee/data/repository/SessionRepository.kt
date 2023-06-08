package com.bangkit.coffee.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.bangkit.coffee.data.source.datastore.UserPreferencesKeys
import com.bangkit.coffee.di.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun flow(): Flow<String?> {
        return context.dataStore.data.map { pref ->
            pref[UserPreferencesKeys.token]
        }
    }

    suspend fun updateToken(token: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.token] = token
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { pref ->
            pref.clear()
        }
    }
}