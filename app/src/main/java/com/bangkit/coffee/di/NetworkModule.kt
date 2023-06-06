package com.bangkit.coffee.di

import com.bangkit.coffee.BuildConfig
import com.bangkit.coffee.data.repository.SessionRepository
import com.bangkit.coffee.data.source.remote.AuthService
import com.bangkit.coffee.data.source.remote.ImageDetectionService
import com.bangkit.coffee.data.source.remote.ProfileService
import com.bangkit.coffee.shared.const.AUTH_BASE_URL
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
        sessionRepository: SessionRepository
    ): Interceptor {
        return Interceptor { chain ->
            val token = runBlocking { sessionRepository.flow().first() }
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
    }

    @Singleton
    @Provides
    @Named("AuthRetrofit")
    fun provideAuthRetrofit(
        loggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("ProfileRetrofit")
    fun provideProfileRetrofit(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
    ): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("ImageRetrofit")
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
        @Named("ImageRetrofit") retrofit: Retrofit
    ): ImageDetectionService {
        return retrofit.create(ImageDetectionService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileService(
        @Named("ProfileRetrofit") retrofit: Retrofit
    ): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }
}