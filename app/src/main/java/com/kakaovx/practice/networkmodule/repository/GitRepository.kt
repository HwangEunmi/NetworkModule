package com.kakaovx.practice.networkmodule.repository

import com.kakaovx.practice.network.ApiResponse
import com.kakaovx.practice.networkmodule.model.UserInfoResponse

interface GitRepository {
    suspend fun getUserInfo(username: String): ApiResponse<UserInfoResponse>

    suspend fun getUserList(): ApiResponse<List<UserInfoResponse>>
}