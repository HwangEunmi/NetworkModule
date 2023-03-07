package com.kakaovx.practice.networkmodule.data.api

import com.kakaovx.practice.network.Json
import com.kakaovx.practice.networkmodule.data.model.TestTokenResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author Jinny (Hwang)
 *
 * Token 관련 Api
 */
interface TokenApi {
    @GET("/users/emails")
    @Json
    suspend fun renewToken(): Response<TestTokenResponse>
}