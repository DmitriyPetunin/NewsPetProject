package com.example.domain.usecase

import com.example.base.model.list.ListNews
import com.example.domain.repository.NewsRepository
import com.example.presentation.usecase.FetchNewsBySearchInputStringUseCase
import javax.inject.Inject


class FetchNewsBySearchInputStringUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
): FetchNewsBySearchInputStringUseCase {
    override suspend fun invoke(input: String): ListNews {
        return newsRepository.getAllBySearch(input)
    }
}