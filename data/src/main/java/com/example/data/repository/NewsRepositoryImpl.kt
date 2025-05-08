package com.example.data.repository

import com.example.data.mappers.ArticleApiToArticleDomainMapper
import com.example.data.mappers.NewsDetailApiToNewsDetailDomainMapper
import com.example.data.model.list.ArticleApi
import com.example.data.model.list.NewsResponse
import com.example.data.service.NewsService
import com.example.domain.model.detail.NewsDetail
import com.example.domain.model.list.Article
import com.example.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    val newsService: NewsService,
    private val articleDomainMapper: ArticleApiToArticleDomainMapper,
    private val newsDetailDomainMapper: NewsDetailApiToNewsDetailDomainMapper
):NewsRepository {

    override suspend fun getAll(): List<Article> {
        val response:NewsResponse = newsService.getAll()
        val list = response.data
        return list.map { articleApi -> articleDomainMapper.invoke(articleApi) }
    }

    override suspend fun getById(id: String): NewsDetail {
        val response = newsService.getById(id = id)
        return newsDetailDomainMapper.invoke(response)

    }
}