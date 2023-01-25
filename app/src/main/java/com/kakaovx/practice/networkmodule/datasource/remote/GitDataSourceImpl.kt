package com.kakaovx.practice.networkmodule.datasource.remote

import com.kakaovx.practice.network.HttpApiResponse
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.network.GithubApi
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse.Companion.toServerFormat
import com.kakaovx.practice.networkmodule.network.retryIO
import javax.inject.Inject

class GitDataSourceImpl @Inject constructor(
    private val githubApi: GithubApi
) : GitDataSource {
    override suspend fun getUserInfo(username: String): TestServerApiResponse<TestUserInfoResponse> {
        val result = githubApi.getUserInfo(username)
        return HttpApiResponse.of { result }.toServerFormat()
    }

    override suspend fun getUserList(): TestServerApiResponse<List<TestUserListResponse>> {
        val result = githubApi.getUserList()
        return HttpApiResponse.of { result }.toServerFormat()
    }
}