package com.kakaovx.practice.networkmodule.data.network.util_retrofit

import android.content.Context
import android.net.ConnectivityManager
import com.kakaovx.practice.networkmodule.App
import com.kakaovx.practice.networkmodule.data.network.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Jinny (Hwang)
 *
 * Network 상태 체크 Interceptor
 */
open class CheckNetworkInterceptor : Interceptor {

    private val connectivityManager: ConnectivityManager by lazy {
        App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        callExceptionWhenNoInternetConnection()

        val request = chain.request()
        return chain.proceed(request)
    }

    protected fun callExceptionWhenNoInternetConnection() {
        if (!isConnected()) {
            throw NoConnectivityException()
        }
    }

    private fun isConnected() = connectivityManager.activeNetwork != null
}