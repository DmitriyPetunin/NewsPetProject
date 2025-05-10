package com.example.network.model.detail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NewsDetailApi(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("keywords")
    val keywords: String,
    @SerialName("snippet")
    val snippet: String,
    @SerialName("url")
    val url: String,
    @SerialName("image_url")
    val image_url: String,
    @SerialName("language")
    val language: String,
    @SerialName("published_at")
    val published_at: String,
    @SerialName("source")
    val source: String,
    @SerialName("categories")
    val categories: List<String>
)