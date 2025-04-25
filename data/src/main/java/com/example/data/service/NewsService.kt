package com.example.data.service

import com.example.data.model.list.NewsResponse
import com.example.domain.model.detail.NewsDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsService {

    @GET("news/all")
    suspend fun getAll(
        @Query("api_token") api_token: String = "3HYXiaCpd28PzOPNemfUbqQmXWt679FtTU2M36NE",
        @Query("language") language: String = "en",
        @Query("limit") limit: Int = 3
    ): NewsResponse

    @GET("news/uuid/{id}")
    suspend fun getById(
        @Path("id") id: Int,
        @Query("api_token") api_token: String = "3HYXiaCpd28PzOPNemfUbqQmXWt679FtTU2M36NE",
    ): NewsDetail
}