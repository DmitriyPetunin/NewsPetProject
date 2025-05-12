package com.example.domain.repository

import com.example.base.model.detail.NewsDetail
import com.example.base.model.list.ListNews


interface NewsRepository {

    suspend fun getAll():ListNews

    suspend fun getAllBySearch(input:String): ListNews

    suspend fun getById(id:String): NewsDetail

}