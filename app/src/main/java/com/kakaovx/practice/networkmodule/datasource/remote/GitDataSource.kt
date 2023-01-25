package com.kakaovx.practice.networkmodule.datasource.remote

import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse

interface GitDataSource {
    suspend fun getUserInfo(username: String): TestServerApiResponse<TestUserInfoResponse>

    suspend fun getUserList(): TestServerApiResponse<List<TestUserListResponse>>
}