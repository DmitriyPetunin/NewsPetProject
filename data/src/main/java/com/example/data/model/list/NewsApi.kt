package com.example.data.model.list

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val meta: Meta,
    val data: List<ArticleApi>
)

@Serializable
data class Meta(
    val found: Int,
    val returned: Int,
    val limit: Int,
    val page: Int
)

@Serializable
data class ArticleApi(
    val uuid: String,
    val title: String,
    val description: String,
    val keywords: String,
    val snippet: String,
    val url: String,
    val image_url: String,
    val language: String,
    val published_at: String,
    val source: String,
    val categories: List<String>,
    val relevance_score: String?
)