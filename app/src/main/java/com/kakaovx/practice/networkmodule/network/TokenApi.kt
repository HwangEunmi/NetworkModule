package com.kakaovx.practice.networkmodule.network

import com.kakaovx.practice.network.Json
import com.kakaovx.practice.networkmodule.model.TestTokenResponse
import retrofit2.Response
import retrofit2.http.GET

interface TokenApi {
    @GET("/users/emails")
    @Json
    suspend fun renewToken(): Response<TestTokenResponse>
}