package com.kakaovx.practice.networkmodule.datasource.remote

import com.kakaovx.practice.network.HttpApiResponse
import com.kakaovx.practice.networkmodule.model.TestTokenResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse.Companion.toServerFormat
import com.kakaovx.practice.networkmodule.network.TokenApi
import javax.inject.Inject

class TokenRemoteDataSourceImpl (
    private val tokenApi: TokenApi
) : TokenRemoteDataSource {
    override suspend fun renewToken(): TestServerApiResponse<TestTokenResponse> {
        val result = tokenApi.renewToken()
        return HttpApiResponse.of { result }.toServerFormat()
    }
}