package com.kakaovx.practice.networkmodule.repository

import com.kakaovx.practice.networkmodule.datasource.GitDataSource
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.model.UserInfoResponse
import com.kakaovx.practice.networkmodule.network.ServerApiResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import javax.inject.Inject

class GitRepositoryImpl @Inject constructor(
    private val gitDataSource: GitDataSource
) : GitRepository {
    override suspend fun getUserInfo(username: String): TestServerApiResponse<TestUserInfoResponse> {
        val result = gitDataSource.getUserInfo(username)
        // return ServerApiResponse.of { result }
        return TestServerApiResponse.of { result }
    }

    override suspend fun getUserList(): TestServerApiResponse<List<TestUserListResponse>> {
        val result = gitDataSource.getUserList()
        // return ServerApiResponse.of { result }
        return TestServerApiResponse.of { result }
    }
}