package com.example.newspetproject.di

import com.example.data.repository.NewsRepositoryImpl
import com.example.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindNewsRepository(
        impl:NewsRepositoryImpl
    ):NewsRepository
}