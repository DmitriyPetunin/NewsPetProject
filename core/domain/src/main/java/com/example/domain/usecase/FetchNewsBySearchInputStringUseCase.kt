package com.example.domain.usecase

import com.example.domain.model.list.Article
import com.example.domain.repository.NewsRepository
import com.example.presentation.model.ArticlesResult
import javax.inject.Inject

interface FetchNewsBySearchInputStringUseCase {

    suspend fun invoke(input:String): ArticlesResult
}

class FetchNewsBySearchInputStringUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
):FetchNewsBySearchInputStringUseCase {
    override suspend fun invoke(input: String): ArticlesResult {
        return newsRepository.getAllBySearch(input)
    }
}