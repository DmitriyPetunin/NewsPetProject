package com.example.data.mappers

import com.example.data.model.list.ArticleApi
import com.example.domain.model.list.Article
import javax.inject.Inject

class ArticleApiToArticleDomainMapper @Inject constructor(): (ArticleApi) -> Article{
    override fun invoke(articleApi: ArticleApi): Article {
        return Article(
            uuid = articleApi.uuid,
            title = articleApi.title,
            description = articleApi.description,
            keywords = articleApi.keywords,
            snippet = articleApi.snippet,
            url = articleApi.url,
            image_url = articleApi.image_url,
            language = articleApi.language,
            published_at = articleApi.published_at,
            source = articleApi.source,
            categories = articleApi.categories,
            relevance_score = articleApi.relevance_score
        )
    }
}