package com.kakaovx.practice.networkmodule.usecase

import com.kakaovx.practice.networkmodule.di.IoDispatcher
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.repository.GitRepository
import com.kakaovx.practice.networkmodule.usecase.base.ParamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val gitRepository: GitRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ParamUseCase<GetUserInfoUseCase.Params, TestServerApiResponse<TestUserInfoResponse>>(dispatcher) {

    override suspend fun execute(param: Params) = gitRepository.getUserInfo(param.username)

    data class Params(
        val username: String
    )
}