package com.example.presentation.usecase

import com.example.base.model.detail.NewsDetail

interface FetchNewsByIdUseCase {

    suspend fun invoke(id:String): NewsDetail
}