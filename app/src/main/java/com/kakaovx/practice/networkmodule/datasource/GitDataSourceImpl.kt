package com.kakaovx.practice.networkmodule.datasource

import com.kakaovx.practice.network.HttpApiResponse
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.network.GithubApi
import javax.inject.Inject

class GitDataSourceImpl @Inject constructor(
    private val githubApi: GithubApi
) : GitDataSource {
    override suspend fun getUserInfo(username: String): HttpApiResponse<TestUserInfoResponse> {
        val result = githubApi.getUserInfo(username)
        return HttpApiResponse.of { result }
    }

    override suspend fun getUserList(): HttpApiResponse<List<TestUserInfoResponse>> {
        val result = githubApi.getUserList()
        return HttpApiResponse.of { result }
    }
}