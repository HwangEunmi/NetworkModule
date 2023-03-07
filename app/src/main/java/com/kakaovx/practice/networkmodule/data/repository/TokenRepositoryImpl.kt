package com.kakaovx.practice.networkmodule.data.repository

import com.kakaovx.practice.networkmodule.data.datasource.local.TokenLocalDataSource
import com.kakaovx.practice.networkmodule.data.datasource.remote.TokenRemoteDataSource
import com.kakaovx.practice.networkmodule.data.model.TestTokenResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.domain.repository.TokenRepository

/**
 * @author Jinny (Hwang)
 *
 * Token Repository 인터페이스 구현체
 */
class TokenRepositoryImpl constructor(
    private val localDataSource: TokenLocalDataSource,
    private val remoteDataSource: TokenRemoteDataSource
) : TokenRepository {

    override var accessToken: String
        get() = localDataSource.accessToken
        set(value) {
            localDataSource.accessToken = value
        }

    override suspend fun renewToken(): TestServerApiResponse<TestTokenResponse> {
        return remoteDataSource.renewToken()
    }
}