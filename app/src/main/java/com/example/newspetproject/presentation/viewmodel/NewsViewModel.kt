package com.example.newspetproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.list.Article
import com.example.domain.usecase.FetchNewsByIdUseCase
import com.example.domain.usecase.FetchNewsUseCase
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

    init {
        fetchAllNews()
    }

    private fun fetchAllNews(){
        viewModelScope.launch {
            _news.value = fetchNewsUseCase.invoke()
        }
    }

    fun fetchDetailNews(id:String){
        viewModelScope.launch {
            fetchNewsByIdUseCase.invoke(id = id)
        }
    }
}