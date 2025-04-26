package com.example.newspetproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.detail.NewsDetail
import com.example.domain.model.list.Article
import com.example.domain.usecase.FetchNewsByIdUseCase
import com.example.domain.usecase.FetchNewsUseCase
import dagger.Binds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val fetchNewsByIdUseCase: FetchNewsByIdUseCase
):ViewModel() {

    private val _news:MutableLiveData<List<Article>> = MutableLiveData(emptyList())
    val news:LiveData<List<Article>> = _news

    private val _detailNews:MutableLiveData<NewsDetail> = MutableLiveData()
    val detailNews:LiveData<NewsDetail> = _detailNews

    private val _isLoading:MutableLiveData<Boolean> = MutableLiveData()
    val isLoading:LiveData<Boolean> = _isLoading

    init {
        fetchAllNews()
    }

    private fun fetchAllNews(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newsList = fetchNewsUseCase.invoke()
                _news.value = newsList
            } catch (e:Exception){

            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchDetailNews(id:String){
        viewModelScope.launch {
            _detailNews.value = fetchNewsByIdUseCase.invoke(id = id)
        }
    }
}