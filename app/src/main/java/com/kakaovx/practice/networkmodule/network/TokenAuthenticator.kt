package com.kakaovx.practice.networkmodule.network

import android.util.Log
import com.kakaovx.practice.networkmodule.network.exceptions.FailRenewTokenException
import com.kakaovx.practice.networkmodule.network.constant.TOKEN
import com.kakaovx.practice.networkmodule.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private var tokenRepository: TokenRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("THEEND", "authenticate: ${response.request.url}")
        if (response.code == 401) {
            if (requestRenewToken() != null) {
                return getRequest(response)
            } else {
                throw FailRenewTokenException()
            }
        }

        return null
    }

    private fun requestRenewToken(): String? {
        val asyncRenewTokenApi = CoroutineScope(Dispatchers.IO).async {
            renewTokenApi()
        }

        val response = runBlocking {
            asyncRenewTokenApi.await()
        }
        Log.d("THEEND", "asyncRenewTokenApi: $asyncRenewTokenApi")

        return when (response) {
            is TestServerApiResponse.Success -> {
                tokenRepository.accessToken = "새로운 토큰"
                "새로운 토큰"
            }
            else -> null
        }
    }

    private suspend fun renewTokenApi() =
        withContext(Dispatchers.IO) { tokenRepository.renewToken() }

    private fun getRequest(response: Response): Request {
        return response.request.newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", TOKEN)
            // .addHeader("Authorization", "새로운 토큰")
            .build()
    }
}