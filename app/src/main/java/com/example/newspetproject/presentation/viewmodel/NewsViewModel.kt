package com.example.newspetproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.detail.NewsDetail
import com.example.domain.model.list.Article
import com.example.domain.usecase.FetchNewsByIdUseCase
import com.example.domain.usecase.FetchNewsUseCase
import com.example.newspetproject.presentation.NewsScreenState
import com.example.newspetproject.presentation.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val fetchNewsByIdUseCase: FetchNewsByIdUseCase
):ViewModel() {

    private val _state = MutableLiveData<NewsScreenState>(NewsScreenState(emptyList()))
    val state: LiveData<NewsScreenState> = _state

    private val _detailNewsState = MutableLiveData<NewsState>(NewsState.NewsLoading)
    val detailNewsState:LiveData<NewsState> = _detailNewsState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        _state.value = NewsScreenState(listOf(NewsState.NewsLoading))

        viewModelScope.launch {

            val result = runCatching {
                fetchNewsUseCase.invoke()
            }

            _state.value = result.fold(
                onSuccess = {
                    NewsScreenState(it.map { NewsState.NewsContent(it) })
                },
                onFailure = { exception ->
                    when (exception) {
                        is IOException -> {
                            Log.d("TEST-TAG", "error IO")
                            NewsScreenState(listOf(NewsState.NewsError("Ошибка загрузки новостей")))
                        }
                        else -> NewsScreenState(listOf(NewsState.NewsError("неизвестная ошибка")))
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
}