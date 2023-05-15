package com.bangkit.coffee.di

import com.bangkit.coffee.domain.usecase.FormatDateUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun provideFormatDateUseCase(formatDateUseCase: FormatDateUseCase): FormatDateUseCase
}