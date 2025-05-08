package com.example.newspetproject.di

import com.example.domain.repository.NewsRepository
import com.example.domain.usecase.FetchNewsByIdUseCase
import com.example.domain.usecase.FetchNewsByIdUseCaseImpl
import com.example.domain.usecase.FetchNewsUseCase
import com.example.domain.usecase.FetchNewsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {


    @Provides
    fun provideFetchNewsUseCase(newsRepository: NewsRepository): FetchNewsUseCase {
        return FetchNewsUseCaseImpl(newsRepository = newsRepository)
    }


    @Provides
    fun provideFetchNewsByIdUseCase(newsRepository: NewsRepository): FetchNewsByIdUseCase {
        return FetchNewsByIdUseCaseImpl(newsRepository = newsRepository)
    }

}