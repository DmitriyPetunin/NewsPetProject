package com.example.domain.usecase

import com.example.domain.model.detail.NewsDetail
import com.example.domain.repository.NewsRepository
import javax.inject.Inject


interface FetchNewsByIdUseCase{
    suspend fun invoke(id:String):NewsDetail
}

class FetchNewsByIdUseCaseImpl @Inject constructor(
    val newsRepository: NewsRepository
):FetchNewsByIdUseCase {

    override suspend fun invoke(id:String): NewsDetail {
        return newsRepository.getById(id)
    }
}