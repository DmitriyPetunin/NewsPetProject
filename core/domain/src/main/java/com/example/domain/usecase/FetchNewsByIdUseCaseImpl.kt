package com.example.domain.usecase

import com.example.base.model.detail.NewsDetail
import com.example.domain.repository.NewsRepository
import com.example.presentation.usecase.FetchNewsByIdUseCase
import javax.inject.Inject

class FetchNewsByIdUseCaseImpl @Inject constructor(
    val newsRepository: NewsRepository
): FetchNewsByIdUseCase {

    override suspend fun invoke(id:String): NewsDetail {
        return newsRepository.getById(id)
    }
}