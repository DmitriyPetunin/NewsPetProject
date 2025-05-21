package com.example.data.mappers

import com.example.base.model.list.Article
import com.example.network.model.list.ArticleApi
import javax.inject.Inject

class ArticleApiToArticleDomainMapper @Inject constructor(): (ArticleApi?) -> Article {

    override fun invoke(articleApi: ArticleApi?): Article {
        return articleApi?.let {
            Article(
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
        } ?: Article.EMPTY
    }
}