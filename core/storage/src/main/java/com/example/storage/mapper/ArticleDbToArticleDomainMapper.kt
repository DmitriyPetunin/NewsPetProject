package com.example.storage.mapper

import com.example.base.model.list.Article
import com.example.storage.entities.ArticleEntity

import javax.inject.Inject

class ArticleDbToArticleDomainMapper @Inject constructor(): (ArticleEntity?) -> Article {
    override fun invoke(p1: ArticleEntity?): Article {
        return p1?.let {
            Article(
                uuid = it.id,
                title = it.title,
                description = it.description,
                keywords = "",
                snippet = "",
                url = "",
                image_url = it.image_url,
                language = "",
                published_at = it.published_at,
                source = it.source,
                categories = emptyList(),
                relevance_score = null
            )
        } ?: Article.EMPTY
    }
}