package com.kakaovx.practice.networkmodule.repository

import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.model.UserInfoResponse
import com.kakaovx.practice.networkmodule.network.ServerApiResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse

interface GitRepository {
    // suspend fun getUserInfo(username: String): ServerApiResponse<UserInfoResponse>
    suspend fun getUserInfo(username: String): TestServerApiResponse<TestUserInfoResponse>

    // suspend fun getUserList(): ServerApiResponse<List<UserInfoResponse>>
    suspend fun getUserList(): TestServerApiResponse<List<TestUserInfoResponse>>
}