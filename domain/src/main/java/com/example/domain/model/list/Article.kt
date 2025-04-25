package com.example.domain.model.list

class Article(
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