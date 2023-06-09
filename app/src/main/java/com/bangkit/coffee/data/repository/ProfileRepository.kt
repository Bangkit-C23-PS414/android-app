package com.bangkit.coffee.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.bangkit.coffee.data.source.datastore.UserPreferencesKeys
import com.bangkit.coffee.data.source.remote.ProfileService
import com.bangkit.coffee.data.source.remote.response.profile.EditProfileResponseData
import com.bangkit.coffee.data.source.remote.response.profile.UpdateAvatarResponseData
import com.bangkit.coffee.di.dataStore
import com.bangkit.coffee.domain.entity.User
import com.bangkit.coffee.domain.mapper.toExternal
import com.bangkit.coffee.shared.util.parse
import com.bangkit.coffee.shared.wrapper.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
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
                email = pref[UserPreferencesKeys.email] ?: return@map null,
                avatarUrl = pref[UserPreferencesKeys.avatarUrl].orEmpty(),
                blurHash = pref[UserPreferencesKeys.blurHash].orEmpty()
            )
        }
    }

    suspend fun refresh(): Resource<User> {
        return try {
            // Fetch data
            val profile = remoteDataSource.get()
            val user = profile.toExternal()

            // Save
            context.dataStore.edit { pref ->
                pref[UserPreferencesKeys.name] = user.name
                pref[UserPreferencesKeys.email] = user.email
                pref[UserPreferencesKeys.avatarUrl] = user.avatarUrl
                pref[UserPreferencesKeys.blurHash] = user.blurHash
            }

            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error("Something went wrong")
        }
    }

    suspend fun edit(name: String): Resource<EditProfileResponseData> {
        return try {
            // Fetch data
            val response = remoteDataSource.edit(name)

            // Save
            context.dataStore.edit { pref ->
                pref[UserPreferencesKeys.name] = response.data.name
            }

            Resource.Success(response.data)
        } catch (e: HttpException) {
            Resource.Error(e.parse().message)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Something went wrong")
        }
    }

    suspend fun updateAvatar(file: File): Resource<UpdateAvatarResponseData> {
        return try {
            // Fetch data
            val response = remoteDataSource.updateAvatar(
                MultipartBody.Part.createFormData(
                    name = "avatar",
                    filename = file.name,
                    body = file.asRequestBody("image/jpeg".toMediaType())
                )
            )

            // Save
            context.dataStore.edit { pref ->
                pref[UserPreferencesKeys.avatarUrl] = response.data.avatarUrl
                pref[UserPreferencesKeys.blurHash] = response.data.blurHash
            }

            Resource.Success(response.data)
        } catch (e: HttpException) {
            Resource.Error(e.parse().message)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error("Something went wrong")
        }
    }
}