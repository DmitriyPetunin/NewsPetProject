package com.example.data.mappers

import com.example.base.model.detail.NewsDetail
import com.example.network.model.detail.NewsDetailApi
import javax.inject.Inject

class NewsDetailApiToNewsDetailDomainMapper @Inject constructor() : (NewsDetailApi?) -> com.example.base.model.detail.NewsDetail {
    override fun invoke(newsDetailApi: NewsDetailApi?): com.example.base.model.detail.NewsDetail {
        return newsDetailApi?.let {
            com.example.base.model.detail.NewsDetail(
                uuid = newsDetailApi.uuid,
                title = newsDetailApi.title,
                description = newsDetailApi.description,
                keywords = newsDetailApi.keywords,
                snippet = newsDetailApi.snippet,
                url = newsDetailApi.url,
                image_url = newsDetailApi.image_url,
                language = newsDetailApi.language,
                published_at = newsDetailApi.published_at,
                source = newsDetailApi.source,
                categories = newsDetailApi.categories
            )
        } ?: com.example.base.model.detail.NewsDetail.EMPTY
    }
}