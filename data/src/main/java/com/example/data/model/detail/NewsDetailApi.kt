package com.example.data.model.detail

import kotlinx.serialization.Serializable


@Serializable
data class NewsDetailApi(
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
    val categories: List<String>
)