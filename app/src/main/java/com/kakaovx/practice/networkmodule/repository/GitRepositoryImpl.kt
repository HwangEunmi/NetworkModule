package com.kakaovx.practice.networkmodule.repository

import com.kakaovx.practice.networkmodule.network.GithubApi

class GitRepositoryImpl(
    private val githubApi: GithubApi
) : GitRepository {
    override suspend fun getUserInfo(username: String) = githubApi.getUserInfo(username)

    override suspend fun getUserList() = githubApi.getUserList()
}