package com.kakaovx.practice.networkmodule.datasource

import com.kakaovx.practice.network.HttpApiResponse
import com.kakaovx.practice.network.model.IHttpResponse
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.model.UserInfoResponse

interface GitDataSource {
    suspend fun getUserInfo(username: String): HttpApiResponse<TestUserInfoResponse>

    suspend fun getUserList(): HttpApiResponse<List<TestUserListResponse>>
}