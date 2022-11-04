package com.kakaovx.practice.networkmodule.network

import com.kakaovx.practice.network.ApiResponse
import com.kakaovx.practice.networkmodule.model.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("/users/{username}")
    suspend fun getUserInfo(
        @Path("username") username: String
    ): ApiResponse<UserInfoResponse>

    @GET("/users")
    suspend fun getUserList(): ApiResponse<List<UserInfoResponse>>
}