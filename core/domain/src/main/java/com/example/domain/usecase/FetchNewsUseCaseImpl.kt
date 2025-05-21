package com.example.domain.usecase

import com.example.base.model.list.ListNews
import com.example.domain.repository.NewsRepository
import com.example.presentation.usecase.FetchNewsUseCase
import javax.inject.Inject


class FetchNewsUseCaseImpl @Inject constructor(
    val newsRepository: NewsRepository
): FetchNewsUseCase {
    override suspend fun invoke(): ListNews {
        return newsRepository.getAll()
    }
}