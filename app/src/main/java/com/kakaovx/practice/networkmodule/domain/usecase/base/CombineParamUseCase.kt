package com.kakaovx.practice.networkmodule.domain.usecase.base

import com.kakaovx.practice.network.constant.ErrorType
import com.kakaovx.practice.networkmodule.data.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.data.network.constant.ServerStatusCode

private typealias ResultCallback = (Any) -> Unit

/**
 * @author Jinny (Hwang)
 *
 * Combine용 Param UseCase
 */
abstract class CombineParamUseCase<in P> {
    suspend operator fun invoke(parameters: P): Any {
        return execute(parameters)
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P): Any

    protected var combineResult: Any = TestServerApiResponse.Failure.Error<Any>(
        errorType = ErrorType.TYPE_SERVER_ERROR,
        errorCode = ServerStatusCode.HttpError
    )

    protected val resultCallback: ResultCallback = object : ResultCallback {
        override fun invoke(result: Any) {
            combineResult = result
        }
    }
}