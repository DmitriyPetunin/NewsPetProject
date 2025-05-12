package com.example.domain.di

import com.example.domain.repository.NewsRepository
import com.example.domain.usecase.FetchNewsByIdUseCaseImpl
import com.example.domain.usecase.FetchNewsBySearchInputStringUseCaseImpl
import com.example.domain.usecase.FetchNewsUseCaseImpl
import com.example.presentation.usecase.FetchNewsByIdUseCase
import com.example.presentation.usecase.FetchNewsBySearchInputStringUseCase
import com.example.presentation.usecase.FetchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DomainModule {


    @Provides
    fun provideFetchNewsUseCase(
        newsRepository: NewsRepository
    ): FetchNewsUseCase {
        return FetchNewsUseCaseImpl(newsRepository = newsRepository)
    }

    @Provides
    fun provideFetchNewsByIdUseCase(
        newsRepository: NewsRepository
    ): FetchNewsByIdUseCase {
        return FetchNewsByIdUseCaseImpl(newsRepository = newsRepository)
    }

    @Provides
    fun provideFetchNewsBySearchInputStringUseCase(
        newsRepository: NewsRepository
    ): FetchNewsBySearchInputStringUseCase {
        return FetchNewsBySearchInputStringUseCaseImpl(newsRepository = newsRepository)
    }

}