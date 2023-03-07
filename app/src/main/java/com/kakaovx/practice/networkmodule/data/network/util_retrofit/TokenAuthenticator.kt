package com.kakaovx.practice.networkmodule.data.network.util_retrofit

import android.util.Log
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.data.network.constant.TOKEN
import com.kakaovx.practice.networkmodule.data.network.exceptions.FailRenewTokenException
import com.kakaovx.practice.networkmodule.domain.repository.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * @author Jinny (Hwang)
 *
 * Token 만료시 갱신하기 위한 Authenticator
 */
class TokenAuthenticator(
    private var tokenRepository: TokenRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d("THEEND", "authenticate: ${response.request.url}, code: ${response.code}")
        if (response.code == 401) {
            if (requestRenewToken() != null) {
                return getRequest(response)
            } else {
                throw FailRenewTokenException()
            }
        }

        return null
    }

    /**
     * @author Jinny (Hwang)
     *
     * Token 갱신하는 로직 수행하기
     */
    private fun requestRenewToken(): String? {
        val asyncRenewTokenApi = CoroutineScope(Dispatchers.IO).async {
            renewTokenApi()
        }

        val response = runBlocking {
            asyncRenewTokenApi.await()
        }
        Log.d("THEEND", "asyncRenewTokenApi: $asyncRenewTokenApi")

        return when (response) {
            is TestServerApiResponse.Success<*> -> {
                tokenRepository.accessToken = "새로운 토큰"
                "새로운 토큰"
            }
            else -> null
        }
    }

    /**
     * @author Jinny (Hwang)
     *
     * Token 갱신하는 Api 호출하기
     */
    private suspend fun renewTokenApi() =
        withContext(Dispatchers.IO) { tokenRepository.renewToken() }

    /**
     * @author Jinny (Hwang)
     *
     * 갱신된 Token으로 만들어진 Request 생성하기
     */
    private fun getRequest(response: Response): Request {
        return response.request.newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", TOKEN)
            // .addHeader("Authorization", "새로운 토큰")
            .build()
    }
}