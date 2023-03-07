package com.kakaovx.practice.networkmodule.data.datasource.remote

import com.kakaovx.practice.network.HttpApiResponse
import com.kakaovx.practice.networkmodule.data.api.TokenApi
import com.kakaovx.practice.networkmodule.data.model.TestTokenResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse.Companion.toServerFormat

/**
 * @author Jinny (Hwang)
 *
 * Token Remote DataSource 인터페이스 구현체
 */
class TokenRemoteDataSourceImpl (
    private val tokenApi: TokenApi
) : TokenRemoteDataSource {
    override suspend fun renewToken(): TestServerApiResponse<TestTokenResponse> {
        val result = tokenApi.renewToken()
        return HttpApiResponse.of { result }.toServerFormat()
    }
}