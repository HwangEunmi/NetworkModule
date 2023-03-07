package com.kakaovx.practice.networkmodule.data.datasource.remote

import com.kakaovx.practice.network.HttpApiResponse
import com.kakaovx.practice.networkmodule.data.api.GithubApi
import com.kakaovx.practice.networkmodule.data.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.data.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse.Companion.toServerFormat
import javax.inject.Inject

/**
 * @author Jinny (Hwang)
 *
 * Github Remote DataSource 인터페이스 구현체
 */
class GitRemoteDataSourceImpl @Inject constructor(
    private val githubApi: GithubApi
) : GitRemoteDataSource {
    override suspend fun getUserInfo(username: String): TestServerApiResponse<TestUserInfoResponse> {
        val result = githubApi.getUserInfo(username)
        return HttpApiResponse.of { result }.toServerFormat()
    }

    override suspend fun getUserList(): TestServerApiResponse<List<TestUserListResponse>> {
        val result = githubApi.getUserList()
        return HttpApiResponse.of { result }.toServerFormat()
    }
}