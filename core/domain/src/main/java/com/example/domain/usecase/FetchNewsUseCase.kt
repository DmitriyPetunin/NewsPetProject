package com.example.domain.usecase

import com.example.domain.model.list.Article
import com.example.domain.repository.NewsRepository
import com.example.presentation.model.ArticlesResult
import javax.inject.Inject


interface FetchNewsUseCase{
    suspend fun invoke(): ArticlesResult
}

class FetchNewsUseCaseImpl @Inject constructor(
    val newsRepository: NewsRepository
):FetchNewsUseCase {
    override suspend fun invoke(): ArticlesResult {
        return newsRepository.getAll()
    }
}