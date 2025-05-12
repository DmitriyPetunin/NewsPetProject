package com.example.base.state

data class NewsScreenState(
    val news: List<NewsState>,
    val source: SourceStatus
)

sealed class NewsState {
    object NewsLoading : NewsState()
    data class NewsContent(val item: Any) : NewsState()
    data class NewsError(val message: String) : NewsState()
}