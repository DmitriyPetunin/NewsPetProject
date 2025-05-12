package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.state.NewsScreenState
import com.example.base.state.NewsState
import com.example.base.state.SourceStatus
import com.example.presentation.usecase.FetchNewsByIdUseCase
import com.example.presentation.usecase.FetchNewsBySearchInputStringUseCase
import com.example.presentation.usecase.FetchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val fetchNewsByIdUseCase: FetchNewsByIdUseCase,
    private val fetchNewsBySearchInputStringUseCase: FetchNewsBySearchInputStringUseCase
):ViewModel() {

    private val _state = MutableStateFlow(
        NewsScreenState(
            emptyList(),
            source = SourceStatus.LOADING
        )
    )
    val state: StateFlow<NewsScreenState> = _state

    private val _detailNewsState = MutableStateFlow<NewsState>(NewsState.NewsLoading)
    val detailNewsState:StateFlow<NewsState> = _detailNewsState

    private val _searchNewsState = MutableStateFlow(
        NewsScreenState(
            emptyList(),
            source = SourceStatus.LOADING
        )
    )
    val searchNewsState: StateFlow<NewsScreenState> = _searchNewsState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {

            val result = runCatching {
                fetchNewsUseCase.invoke()
            }

            _state.value = result.fold(
                onSuccess = { articlesResult ->
                    when (articlesResult.source) {
                        SourceStatus.API -> {
                            NewsScreenState(
                                news = articlesResult.articles.map {
                                    NewsState.NewsContent(
                                        it
                                    )
                                },
                                source = SourceStatus.API
                            )
                        }
                        SourceStatus.DB -> {
                            NewsScreenState(
                                news = articlesResult.articles.map {
                                    NewsState.NewsContent(
                                        it
                                    )
                                },
                                source = SourceStatus.DB
                            )
                        }

                        SourceStatus.ERROR -> TODO()
                        SourceStatus.LOADING -> TODO()
                    }
                },
                onFailure = { exception ->
                    when (exception) {
                        is IOException -> {
                            Log.d("TEST-TAG", "error IO")
                            NewsScreenState(
                                news = listOf(NewsState.NewsError("Ошибка загрузки новостей")),
                                source = SourceStatus.ERROR
                            )
                        }
                        else -> {
                            exception.message?.let { Log.d("TEST-TAG", it) }
                            NewsScreenState(
                                news = listOf(NewsState.NewsError("Другая ошибка")),
                                source = SourceStatus.ERROR
                            )
                        }
                    }
                }
            )
        }
    }

    fun fetchDetailNews(id: String) {

        _detailNewsState.value = NewsState.NewsLoading

        viewModelScope.launch {

            val result = runCatching {
                fetchNewsByIdUseCase.invoke(id = id)
            }

            _detailNewsState.value = result.fold(
                onSuccess = {
                    NewsState.NewsContent(it)
                },
                onFailure = { exception ->
                    when (exception) {
                        is IOException -> {
                            Log.d("TEST-TAG", "error IO")
                            NewsState.NewsError("Ошибка загрузки новости с id $id")
                        }
                        else -> NewsState.NewsError("неизвестная ошибка")
                    }
                }
            )
        }
    }

    fun search(input: String) {
        viewModelScope.launch {

            val result = runCatching {
                fetchNewsBySearchInputStringUseCase.invoke(input)
            }

            _searchNewsState.value = result.fold(
                onSuccess = { articlesResult ->
                    when (articlesResult.source) {
                        SourceStatus.API -> {
                            NewsScreenState(
                                news = articlesResult.articles.map {
                                    NewsState.NewsContent(
                                        it
                                    )
                                },
                                source = SourceStatus.API
                            )
                        }
                        SourceStatus.DB -> {
                            NewsScreenState(
                                news = articlesResult.articles.map {
                                    NewsState.NewsContent(
                                        it
                                    )
                                },
                                source = SourceStatus.DB
                            )
                        }

                        SourceStatus.ERROR -> TODO()
                        SourceStatus.LOADING -> TODO()
                    }
                },
                onFailure = { exception ->
                    when (exception) {
                        is IOException -> {
                            Log.d("TEST-TAG", "error IO")
                            NewsScreenState(
                                news = listOf(NewsState.NewsError("Ошибка загрузки новостей")),
                                source = SourceStatus.ERROR
                            )
                        }
                        else -> {
                            exception.message?.let { Log.d("TEST-TAG", it) }
                            NewsScreenState(
                                news = listOf(NewsState.NewsError("Другая ошибка")),
                                source = SourceStatus.ERROR
                            )
                        }
                    }
                }
            )
        }
    }
}