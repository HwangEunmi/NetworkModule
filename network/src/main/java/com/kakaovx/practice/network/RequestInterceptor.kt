package com.kakaovx.practice.network

import okhttp3.Interceptor
import okhttp3.Response

// TODO : 추후에 따로 빼기
private val TOKEN = "token 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"

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val requestBuilder = originalRequest.newBuilder().url(originalUrl)
        val request = requestBuilder
            .header("Authorization", TOKEN).build()

        return chain.proceed(request)
    }
}