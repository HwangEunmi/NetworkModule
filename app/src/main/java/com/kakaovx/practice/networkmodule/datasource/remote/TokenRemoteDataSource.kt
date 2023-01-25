package com.kakaovx.practice.networkmodule.datasource.remote

import com.kakaovx.practice.networkmodule.model.TestTokenResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse

interface TokenRemoteDataSource {
    suspend fun renewToken(): TestServerApiResponse<TestTokenResponse>
}