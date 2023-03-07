package com.kakaovx.practice.networkmodule.domain.usecase

import com.kakaovx.practice.networkmodule.data.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.di.IoDispatcher
import com.kakaovx.practice.networkmodule.domain.repository.GitRepository
import com.kakaovx.practice.networkmodule.domain.usecase.base.ParamUseCase
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