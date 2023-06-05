package com.bangkit.coffee.di

import com.bangkit.coffee.BuildConfig
import com.bangkit.coffee.data.repository.UserPreferencesRepository
import com.bangkit.coffee.data.source.remote.ImageDetectionService
import com.bangkit.coffee.shared.const.IMAGE_API_URL
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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(
        userPreferencesRepository: UserPreferencesRepository
    ): Interceptor {
        return Interceptor { chain ->
            val token = runBlocking { userPreferencesRepository.tokenFlow.first() }
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
    }

    @Singleton
    @Provides
    @Named("AuthService")
    fun provideAuthRetrofit(
        loggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("ImageService")
    fun provideImageRetrofit(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
    ): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(IMAGE_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideImageDetectionService(
        @Named("ImageService") retrofit: Retrofit
    ): ImageDetectionService {
        return retrofit.create(ImageDetectionService::class.java)
    }
}