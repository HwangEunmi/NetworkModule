package com.kakaovx.practice.network

import com.kakaovx.practice.network.constant.TOKEN
import okhttp3.Interceptor
import okhttp3.Response

abstract class BaseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val requestBuilder = originalRequest.newBuilder().url(originalUrl)

        val request = requestBuilder
            .header("Authorization", TOKEN).build()
        return chain.proceed(request)
    }
}