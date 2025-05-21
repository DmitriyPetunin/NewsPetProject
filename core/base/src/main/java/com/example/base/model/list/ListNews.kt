package com.example.base.model.list

import com.example.base.state.SourceStatus

class ListNews(
    val source: SourceStatus = SourceStatus.LOADING,
    val articles:List<Article>
)