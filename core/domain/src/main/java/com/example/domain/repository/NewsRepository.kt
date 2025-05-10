package com.example.domain.repository

import com.example.domain.model.detail.NewsDetail
import com.example.domain.model.list.Article
import com.example.presentation.model.ArticlesResult


interface NewsRepository {

    suspend fun getAll():ArticlesResult

    suspend fun getAllBySearch(input:String): ArticlesResult

    suspend fun getById(id:String): NewsDetail

}