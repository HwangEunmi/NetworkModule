package com.kakaovx.practice.networkmodule.repository

import com.kakaovx.practice.networkmodule.network.GithubApi

class GitRepositoryImpl(
    private val service: GithubApi
) : GitRepository {
    override suspend fun getUserInfo(username: String) = service.getUserInfo(username)

    override suspend fun getUserList() = service.getUserList()
}