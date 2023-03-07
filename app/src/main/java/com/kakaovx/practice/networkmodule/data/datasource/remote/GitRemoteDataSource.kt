package com.kakaovx.practice.networkmodule.data.datasource.remote

import com.kakaovx.practice.networkmodule.data.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.data.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse

/**
 * @author Jinny (Hwang)
 *
 * Github Remote DataSource 인터페이스
 */
interface GitRemoteDataSource {
    suspend fun getUserInfo(username: String): TestServerApiResponse<TestUserInfoResponse>

    suspend fun getUserList(): TestServerApiResponse<List<TestUserListResponse>>
}