package com.kakaovx.practice.networkmodule.network

import android.content.Context
import android.net.ConnectivityManager
import com.kakaovx.practice.network.BaseInterceptor
import com.kakaovx.practice.networkmodule.App
import com.kakaovx.practice.networkmodule.network.constant.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : BaseInterceptor() {

    private val connectivityManager: ConnectivityManager by lazy {
        App.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw NoConnectivityException()
        }
        return super.intercept(chain)
    }

    private fun isConnected() = connectivityManager.activeNetwork != null
}