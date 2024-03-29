package com.kakaovx.practice.networkmodule.data.network.util_retrofit

import android.util.Log
import com.kakaovx.practice.networkmodule.data.network.constant.TOKEN
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Jinny (Hwang)
 *
 * 헤더 추가 Interceptor
 */
class AddHeaderInterceptor : CheckNetworkInterceptor() {
    // var num = 0

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("THEEND", "call interceptor: ${chain.request().url}")
        callExceptionWhenNoInternetConnection()
        // num += 1

        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val requestBuilder = originalRequest.newBuilder().url(originalUrl)

        // if (num == 1) {
        //     val response = chain.proceed(chain.request())
        //     return response.newBuilder().code(401).body("{}".toResponseBody()).build()
        // } else {
        val request = requestBuilder.header("Authorization", TOKEN).build()

        return chain.proceed(request)
        // }
    }
}