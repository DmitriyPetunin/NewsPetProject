package com.example.data.repository

import com.example.data.mappers.ArticleApiToArticleDomainMapper
import com.example.data.mappers.NewsDetailApiToNewsDetailDomainMapper
import com.example.base.model.detail.NewsDetail
import com.example.base.model.list.Article
import com.example.base.model.list.ListNews
import com.example.base.state.SourceStatus
import com.example.domain.repository.NewsRepository
import com.example.network.model.list.NewsResponse
import com.example.network.service.NewsService
import com.example.storage.RequestType
import com.example.storage.dao.NewsDao
import com.example.storage.entities.ArticleEntity
import com.example.storage.entities.RequestEntity
import com.example.storage.mapper.ArticleDbToArticleDomainMapper
import java.util.UUID
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService,
    private val articleDomainMapper: ArticleApiToArticleDomainMapper,
    private val newsDetailDomainMapper: NewsDetailApiToNewsDetailDomainMapper,
    private val articleDbToArticleDomainMapper: ArticleDbToArticleDomainMapper,
    private val newsDao: NewsDao
):NewsRepository {

    override suspend fun getAll(): ListNews {
        val response: NewsResponse = newsService.getAll()
        val list = response.data
        return ListNews(articles = list.map { articleApi -> articleDomainMapper.invoke(articleApi)}, source = SourceStatus.API)
    }

    override suspend fun getById(id: String): NewsDetail {
        val response = newsService.getById(id = id)
        return newsDetailDomainMapper.invoke(response)
    }

    override suspend fun getAllBySearch(input: String): ListNews {

        val req = newsDao.getReqByQuery(input)

        if (req == null){ // пустой

            val response: NewsResponse = newsService.getAllBySearch(input)
            val list = response.data

            val idReq = UUID.randomUUID().toString()

            newsDao.insertRequest(
                RequestEntity(
                    id = idReq,
                    query = input,
                    type = RequestType.SEARCH,
                    timestamp = System.currentTimeMillis()
                )
            )

            newsDao.insertArticles(list.map { it -> ArticleEntity(
                id = UUID.randomUUID().toString(),
                title = it.title,
                description = it.description,
                image_url = it.image_url,
                published_at = it.published_at,
                source = it.source,
                requestId = idReq
            ) })

            return ListNews(source = SourceStatus.API, articles = list.map { articleDomainMapper.invoke(it) })

            //возвращаем API, сохраняем в кеш(req,article)
        } else{ // не пустой

            val currentTime = System.currentTimeMillis()
            if (currentTime - req.timestamp > 5.minutes) { // много прошло

                val response: NewsResponse = newsService.getAllBySearch(input)
                val list = response.data


                newsDao.deleteOldRequests(id = req.id)

                val idReq = UUID.randomUUID().toString()

                newsDao.insertRequest(
                    RequestEntity(
                        id = idReq,
                        query = input,
                        type = RequestType.SEARCH,
                        timestamp = System.currentTimeMillis()
                    )
                )

                newsDao.insertArticles(list.map { it -> ArticleEntity(
                    id = UUID.randomUUID().toString(),
                    title = it.title,
                    description = it.description,
                    image_url = it.image_url,
                    published_at = it.published_at,
                    source = it.source,
                    requestId = idReq
                ) })

                //удалить, записать новые req,article
                return ListNews(source = SourceStatus.API, articles = list.map { articleDomainMapper.invoke(it) })
            } else { // кеш актуален
                val list = newsDao.getArticlesByRequestId(req.id)
                return ListNews(source = SourceStatus.DB, articles = list.map { articleDbToArticleDomainMapper.invoke(it) } )
            }
        }
    }
}

val Int.minutes: Long
    get() = this * 60 * 1000L