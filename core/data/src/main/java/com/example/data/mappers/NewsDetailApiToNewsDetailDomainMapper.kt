package com.example.data.mappers

import com.example.domain.model.detail.NewsDetail
import com.example.network.model.detail.NewsDetailApi
import javax.inject.Inject

class NewsDetailApiToNewsDetailDomainMapper @Inject constructor() : (NewsDetailApi?) -> NewsDetail {
    override fun invoke(newsDetailApi: NewsDetailApi?): NewsDetail {
        return newsDetailApi?.let {
            NewsDetail(
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
        } ?: NewsDetail.EMPTY
    }
}