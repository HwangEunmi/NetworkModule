package com.kakaovx.practice.networkmodule.repository

import com.kakaovx.practice.networkmodule.datasource.remote.GitDataSource
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import javax.inject.Inject

class GitRepositoryImpl @Inject constructor(
    private val gitRemoteDataSource: GitDataSource
) : GitRepository {

    override suspend fun getUserInfo(username: String): TestServerApiResponse<TestUserInfoResponse> {
        return gitRemoteDataSource.getUserInfo(username)
    }

    override suspend fun getUserList(): TestServerApiResponse<List<TestUserListResponse>> {
        return gitRemoteDataSource.getUserList()
    }
}