package com.kakaovx.practice.networkmodule.repository

import com.kakaovx.practice.networkmodule.model.TestTokenResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse

interface TokenRepository {
    suspend fun renewToken(): TestServerApiResponse<TestTokenResponse>

    var accessToken: String
}