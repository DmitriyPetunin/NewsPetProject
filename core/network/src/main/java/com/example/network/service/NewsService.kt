package com.example.network.service


import com.example.network.model.detail.NewsDetailApi
import com.example.network.model.list.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsService {

    @GET("news/all")
    suspend fun getAll(
        @Query("language") language: String = "en",
        @Query("limit") limit: Int = 3
    ): NewsResponse

    @GET("news/uuid/{id}")
    suspend fun getById(
        @Path("id") id: String,
    ): NewsDetailApi

    @GET("news/all")
    suspend fun getAllBySearch(
        @Query("search") input:String,
        @Query("language") language: String = "en",
    ): NewsResponse
}