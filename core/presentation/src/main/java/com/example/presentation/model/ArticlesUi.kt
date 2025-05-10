package com.example.presentation.model


sealed class ArticlesResult {
    data class FromApi(val articles: List<Any>) : ArticlesResult()
    data class FromDb(val articles: List<Any>) : ArticlesResult()
}