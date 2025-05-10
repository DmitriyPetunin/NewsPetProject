package com.example.network.interceptor

import com.example.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor() :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val newUrl = req.url
            .newBuilder()
            .addQueryParameter("api_token", BuildConfig.API_KEY).build()

        val newRequest = req.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}