package com.example.domain.repository

import com.example.domain.model.detail.NewsDetail
import com.example.domain.model.list.Article


interface NewsRepository {


    suspend fun getAll():List<Article>

    suspend fun getById(id:String): NewsDetail

}