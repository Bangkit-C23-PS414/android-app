package com.bangkit.coffee.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.bangkit.coffee.data.source.datastore.UserPreferencesKeys
import com.bangkit.coffee.data.source.remote.ProfileService
import com.bangkit.coffee.di.dataStore
import com.bangkit.coffee.domain.entity.User
import com.bangkit.coffee.domain.mapper.toExternal
import com.bangkit.coffee.shared.wrapper.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val remoteDataSource: ProfileService,
) {

    fun get(): Flow<User?> {
        return context.dataStore.data.map { pref ->
            User(
                name = pref[UserPreferencesKeys.name] ?: return@map null,
                email = pref[UserPreferencesKeys.email] ?: return@map null
            )
        }
    }

    suspend fun refresh(): Resource<User> {
        return try {
            // Fetch data
            val profile = remoteDataSource.get()

            // Save
            context.dataStore.edit { pref ->
                pref[UserPreferencesKeys.name] = profile.name
                pref[UserPreferencesKeys.email] = profile.email
            }

            Resource.Success(profile.toExternal())
        } catch (e: Exception) {
            Resource.Error("Something went wrong")
        }
    }
}