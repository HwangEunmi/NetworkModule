package com.kakaovx.practice.networkmodule.usecase

import com.kakaovx.practice.network.ApiResponse
import com.kakaovx.practice.networkmodule.model.UserInfoResponse
import com.kakaovx.practice.networkmodule.repository.GitRepository

class GetUserInfoUseCase(private val repository: GitRepository) {
    suspend operator fun invoke(params: Params): ApiResponse<UserInfoResponse> {
        return repository.getUserInfo(params.username)
    }

    data class Params(
        val username: String
    )
}