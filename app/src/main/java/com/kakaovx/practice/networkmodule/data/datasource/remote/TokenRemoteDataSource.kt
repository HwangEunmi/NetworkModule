package com.kakaovx.practice.networkmodule.data.datasource.remote

import com.kakaovx.practice.networkmodule.data.model.TestTokenResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse

/**
 * @author Jinny (Hwang)
 *
 * Token Remote DataSource 인터페이스
 */
interface TokenRemoteDataSource {
    suspend fun renewToken(): TestServerApiResponse<TestTokenResponse>
}