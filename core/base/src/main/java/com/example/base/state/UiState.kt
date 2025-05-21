package com.example.base.state

import androidx.compose.runtime.Immutable

@Immutable
data class NewsScreenState(
    val news: List<NewsState>,
    val source: SourceStatus
)

@Immutable
sealed class NewsState {
    object NewsLoading : NewsState()
    data class NewsContent(val item: Any) : NewsState()
    data class NewsError(val message: String) : NewsState()
}