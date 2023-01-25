package com.kakaovx.practice.networkmodule.usecase

import com.kakaovx.practice.network.constant.ErrorType
import com.kakaovx.practice.networkmodule.di.IoDispatcher
import com.kakaovx.practice.networkmodule.model.CombineUserInfoResponse
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.network.combines
import com.kakaovx.practice.networkmodule.network.constant.ServerStatusCode
import com.kakaovx.practice.networkmodule.network.toFlow
import com.kakaovx.practice.networkmodule.repository.GitRepository
import com.kakaovx.practice.networkmodule.usecase.base.CombineParamUseCase
import com.kakaovx.practice.networkmodule.util.whenAllNotNull
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserCombineUseCase @Inject constructor(
    private val gitRepository: GitRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : CombineParamUseCase<GetUserCombineUseCase.Params>() {

    override suspend fun execute(param: Params): Any {
        val userInfo = gitRepository.getUserInfo(param.username).toFlow()
        val userINfo2 = gitRepository.getUserInfo(param.username).toFlow()
        // val userList = gitRepository.getUserList().toFlow()

        // combines(userInfo, userList, dispatcher = dispatcher,
        combines(userInfo, userINfo2, dispatcher = dispatcher,
            onSuccessResult = {
                var testUserInfoResponse: TestUserInfoResponse? = null
                var testUserListResponse2: TestUserInfoResponse? = null
                // var testUserListResponse: List<TestUserListResponse>? = null

                this.forEach { result ->
                    val successData = result as TestServerApiResponse.Success
                    when (successData.data) {
                        is TestUserInfoResponse -> {
                            if (testUserInfoResponse == null) testUserInfoResponse = successData.data
                            else testUserListResponse2 = successData.data
                        }

                        // is TestUserInfoResponse -> testUserInfoResponse = successData.data
                        // else -> testUserListResponse = successData.data as List<TestUserListResponse>
                        // 실제 프로젝트에서는 data 자체가 List로 내려올 일 없음 (code, msg와 같은 라인인 data)
                    }
                }

                // whenAllNotNull(testUserInfoResponse, testUserListResponse,
                whenAllNotNull(testUserInfoResponse, testUserListResponse2,
                    notNullBlock = {
                        resultCallback(
                            TestServerApiResponse.Success(
                                CombineUserInfoResponse(
                                    userInfoResponse = testUserInfoResponse!!,
                                    userInfoResponse2 = testUserListResponse2!!
                                    // userListResponse = testUserListResponse!!

                                )
                            )
                        )
                    }, nullBlock = {
                        // TODO : 무슨 에러로 정의할지 생각해보기
                        resultCallback(
                            TestServerApiResponse.Failure.Error<CombineUserInfoResponse>(
                                errorType = ErrorType.TYPE_SERVER_ERROR,
                                errorCode = ServerStatusCode.HttpError
                            )
                        )
                    })
            },

            onFailResult = {
                resultCallback(this)
            })

        return combineResult
    }

    data class Params(
        val username: String
    )
}