package com.kakaovx.practice.networkmodule.usecase.base

import com.kakaovx.practice.network.constant.ApiErrorType
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.network.constant.ServerStatusCode

typealias ResultCallback = (Any) -> Unit

abstract class CombineParamUseCase<in P> {
    suspend operator fun invoke(parameters: P): Any {
        return execute(parameters)
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P): Any

    protected var combineResult: Any = TestServerApiResponse.Failure.Error<Any>(
        errorType = ApiErrorType.TYPE_SERVER_ERROR,
        errorCode = ServerStatusCode.HttpError
    )

    protected val resultCallback: ResultCallback = object : ResultCallback {
        override fun invoke(result: Any) {
            combineResult = result
        }
    }
}