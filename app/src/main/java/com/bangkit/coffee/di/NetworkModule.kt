package com.bangkit.coffee.di

import com.bangkit.coffee.BuildConfig
import com.bangkit.coffee.data.repository.UserPreferencesRepository
import com.bangkit.coffee.data.source.remote.ImageDetectionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(userPreferencesRepository: UserPreferencesRepository): Retrofit {
        val logging = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
            else HttpLoggingInterceptor.Level.NONE
        )

        val authInterceptor = Interceptor { chain ->
            val token = runBlocking { userPreferencesRepository.tokenFlow.first() }
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder().baseUrl("").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideImageDetectionService(retrofit: Retrofit): ImageDetectionService {
        return retrofit.create(ImageDetectionService::class.java)
    }
}