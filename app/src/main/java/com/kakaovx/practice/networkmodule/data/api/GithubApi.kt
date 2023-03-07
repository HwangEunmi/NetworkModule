package com.kakaovx.practice.networkmodule.data.api

import com.kakaovx.practice.network.Json
import com.kakaovx.practice.network.Scalars
import com.kakaovx.practice.networkmodule.data.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.data.model.TestUserListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Jinny (Hwang)
 *
 * Github 관련 Api
 */
interface GithubApi {
    @GET("/users/{username}")
    @Json
    suspend fun getUserInfo(@Path("username") username: String): Response<TestUserInfoResponse>

    @GET("/users")
    @Json
    // @Scalars
    suspend fun getUserList(): Response<List<TestUserListResponse>>
}