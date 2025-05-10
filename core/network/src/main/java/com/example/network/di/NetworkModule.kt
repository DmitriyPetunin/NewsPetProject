package com.example.network.di

import android.annotation.SuppressLint
import com.example.network.BuildConfig
import com.example.network.interceptor.ApiKeyInterceptor
import com.example.network.BuildConfig as networkConfig
import com.example.network.service.NewsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideNewsService(
        retrofit: Retrofit
    ): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {

        val converter = "application/json".toMediaType()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(converter))
            .build()
    }

    @Provides
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        val builder = if (networkConfig.DEBUG) {
            getUnsafeOkHttpClientBuilder()
        } else {
            OkHttpClient.Builder()
        }

        builder.addInterceptor(apiKeyInterceptor)

        return builder.build()
    }

    @SuppressLint("CustomX509TrustManager")
    private fun getUnsafeOkHttpClientBuilder(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
                okHttpClient.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts.first() as X509TrustManager
                )
                okHttpClient.hostnameVerifier { _, _ -> true }
            }

            return okHttpClient
        } catch (e: Exception) {
            return okHttpClient
        }
    }

}