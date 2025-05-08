package com.example.newspetproject.presentation

data class NewsScreenState(val news: List<NewsState>)

sealed class NewsState {
    object NewsLoading : NewsState()
    data class NewsContent(val item: Any) : NewsState()
    data class NewsError(val message: String) : NewsState()
}