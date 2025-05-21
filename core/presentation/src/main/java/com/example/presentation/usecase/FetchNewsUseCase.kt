package com.example.presentation.usecase

import com.example.base.model.list.ListNews

interface FetchNewsUseCase {
    suspend fun invoke(): ListNews
}