package com.kakaovx.practice.networkmodule.domain.repository

import com.kakaovx.practice.networkmodule.data.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.data.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse

/**
 * @author Jinny (Hwang)
 *
 * Github Repository 인터페이스
 */
interface GitRepository {
    // suspend fun getUserInfo(username: String): ServerApiResponse<UserInfoResponse>
    suspend fun getUserInfo(username: String): TestServerApiResponse<TestUserInfoResponse>

    // suspend fun getUserList(): ServerApiResponse<List<UserInfoResponse>>
    suspend fun getUserList(): TestServerApiResponse<List<TestUserListResponse>>
}