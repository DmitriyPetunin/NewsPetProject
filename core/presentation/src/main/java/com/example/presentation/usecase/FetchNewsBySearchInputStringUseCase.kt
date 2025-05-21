package com.example.presentation.usecase

import com.example.base.model.list.ListNews

interface FetchNewsBySearchInputStringUseCase {
    suspend fun invoke(input:String): ListNews
}