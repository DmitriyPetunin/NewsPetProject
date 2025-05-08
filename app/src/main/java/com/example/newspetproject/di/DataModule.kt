package com.example.newspetproject.di

import com.example.data.repository.NewsRepositoryImpl
import com.example.data.service.NewsService
import com.example.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Provides
    fun provideNewsService(retrofit: Retrofit):NewsService{
        return retrofit.create(NewsService::class.java)
    }

}