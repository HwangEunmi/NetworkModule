package com.kakaovx.practice.networkmodule.domain.usecase

import com.kakaovx.practice.networkmodule.data.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.di.IoDispatcher
import com.kakaovx.practice.networkmodule.domain.repository.GitRepository
import com.kakaovx.practice.networkmodule.domain.usecase.base.NonParamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val gitRepository: GitRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NonParamUseCase<TestServerApiResponse<List<TestUserListResponse>>>(dispatcher) {

    override suspend fun execute() = gitRepository.getUserList()
}