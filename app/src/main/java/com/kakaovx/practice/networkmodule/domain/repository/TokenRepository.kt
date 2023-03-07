package com.kakaovx.practice.networkmodule.domain.repository

import com.kakaovx.practice.networkmodule.data.model.TestTokenResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse

/**
 * @author Jinny (Hwang)
 *
 * Token Repository 인터페이스
 */
interface TokenRepository {
    suspend fun renewToken(): TestServerApiResponse<TestTokenResponse>

    var accessToken: String
}