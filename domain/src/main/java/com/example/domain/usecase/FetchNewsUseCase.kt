package com.example.domain.usecase

import com.example.domain.model.list.Article
import com.example.domain.repository.NewsRepository
import javax.inject.Inject


interface FetchNewsUseCase{
    suspend fun invoke():List<Article>
}

class FetchNewsUseCaseImpl @Inject constructor(
    val newsRepository: NewsRepository
):FetchNewsUseCase {
    override suspend fun invoke(): List<Article> {
        return newsRepository.getAll()
    }
}