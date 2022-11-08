package com.kakaovx.practice.networkmodule.usecase

import com.kakaovx.practice.network.ApiResponse
import com.kakaovx.practice.networkmodule.model.UserInfoResponse
import com.kakaovx.practice.networkmodule.repository.GitRepository

class GetUserListUseCase(private val repository: GitRepository) {
    suspend operator fun invoke(): ApiResponse<List<UserInfoResponse>> {
        return repository.getUserList()
    }
}