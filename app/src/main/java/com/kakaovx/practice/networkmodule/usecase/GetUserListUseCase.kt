package com.kakaovx.practice.networkmodule.usecase

import com.kakaovx.practice.networkmodule.di.IoDispatcher
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.repository.GitRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val gitRepository: GitRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NonParamUseCase<TestServerApiResponse<List<TestUserInfoResponse>>>(dispatcher) {

    override suspend fun execute() = gitRepository.getUserList()
}