package com.bangkit.coffee.di

import com.bangkit.coffee.BuildConfig
import com.bangkit.coffee.data.source.remote.DetectionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
            else HttpLoggingInterceptor.Level.NONE
        )
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder().baseUrl("").client(client)
            .addConverterFactory(MoshiConverterFactory.create()).build()
    }

    @Provides
    fun provideDetectionService(retrofit: Retrofit): DetectionService {
        return retrofit.create(DetectionService::class.java)
    }
}