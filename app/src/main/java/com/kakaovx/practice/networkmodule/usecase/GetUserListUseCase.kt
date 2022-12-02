package com.kakaovx.practice.networkmodule.usecase

import com.kakaovx.practice.networkmodule.di.IoDispatcher
import com.kakaovx.practice.networkmodule.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.repository.GitRepository
import com.kakaovx.practice.networkmodule.usecase.base.NonParamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val gitRepository: GitRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NonParamUseCase<TestServerApiResponse<List<TestUserListResponse>>>(dispatcher) {

    override suspend fun execute() = gitRepository.getUserList()
}