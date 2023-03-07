package com.kakaovx.practice.networkmodule.data.repository

import com.kakaovx.practice.networkmodule.data.datasource.remote.GitRemoteDataSource
import com.kakaovx.practice.networkmodule.data.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.data.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.domain.repository.GitRepository
import javax.inject.Inject

/**
 * @author Jinny (Hwang)
 *
 * Github Repository 인터페이스 구현체
 */
class GitRepositoryImpl @Inject constructor(
    private val gitRemoteDataSource: GitRemoteDataSource
) : GitRepository {

    override suspend fun getUserInfo(username: String): TestServerApiResponse<TestUserInfoResponse> {
        return gitRemoteDataSource.getUserInfo(username)
    }

    override suspend fun getUserList(): TestServerApiResponse<List<TestUserListResponse>> {
        return gitRemoteDataSource.getUserList()
    }
}