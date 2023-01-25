package com.kakaovx.practice.networkmodule.repository

import com.kakaovx.practice.networkmodule.datasource.local.TokenLocalDataSource
import com.kakaovx.practice.networkmodule.datasource.remote.TokenRemoteDataSource
import com.kakaovx.practice.networkmodule.model.TestTokenResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse

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