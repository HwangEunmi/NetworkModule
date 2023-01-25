package com.kakaovx.practice.networkmodule.network

import com.kakaovx.practice.network.HttpApiResponse
import com.kakaovx.practice.network.constant.ErrorType
import com.kakaovx.practice.networkmodule.network.constant.ServerStatusCode
import com.kakaovx.practice.networkmodule.network.model.ITestServerResponse

/**
 * @author Jinny
 * Retrofit 호출 후 서버 통신 표준 응답을 구성하기 위한 테스트용 인터페이스
 */
open class TestServerApiResponse<out T> {

    data class Success<T>(val response: T) : TestServerApiResponse<T>() {
        val data = response
        override fun toString(): String = "[ServerApiResponse.Success](data=$data)"
    }

    sealed class Failure<T> : TestServerApiResponse<T>() {
        data class Error<T>(
            val errorType: ErrorType = ErrorType.TYPE_HTTP_ERROR,
            val errorCode: ServerStatusCode = ServerStatusCode.HttpError
        ) : Failure<T>() {
            override fun toString(): String = "[ServerApiResponse.Failure.Error-$errorType](errorCode=$errorCode)"
        }

        data class Exception<T>(val exception: Throwable) : Failure<T>() {
            private val message: String? = exception.localizedMessage
            override fun toString(): String = "[ServerApiResponse.Failure.Exception](message+$message)"
        }
    }

    companion object {

        /**
         * @author Jinny
         * ServerApiResponse Factory
         *
         * @param successCode : 서버의 성공코드(E000) (비교군)
         * @param f : HttpApiResponse로 변환된 Response
         *
         * HTTP 통신 성공/실패 구분값인 [HttpApiResponse]를 서버 성공/실패 구분값인 [TestServerApiResponse]로 변환한다.
         */
        fun <T : Any> HttpApiResponse<T>.toServerFormat(): TestServerApiResponse<T> {
            val response = this
            when (response) {
                is HttpApiResponse.Success -> {
                    if (response.data !is ITestServerResponse) {
                        return Failure.Error(
                            errorType = ErrorType.TYPE_SERVER_ERROR,
                            errorCode = getStatusCodeFromApiCode("XXXX")
                        )
                    }

                    return Success(response.data)
                }

                is HttpApiResponse.Failure.Error ->
                    return Failure.Error(
                        errorType = ErrorType.TYPE_HTTP_ERROR,
                        errorCode = ServerStatusCode.HttpError
                    )

                is HttpApiResponse.Failure.Exception ->
                    return Failure.Exception(response.exception)

                else -> throw TypeCastException("This type is not expected.")
            }
        }

        /**
         * Api 상태코드에서 서버 상태코드로 변환한다.
         *
         * @param code : Api 상태코드
         * @return 서버 상태코드
         */
        private fun getStatusCodeFromApiCode(code: String): ServerStatusCode {
            return ServerStatusCode.values().find { it.code == code } ?: ServerStatusCode.HttpError
        }

        /**
         * 토큰 만료 상태인지 확인한다.
         */
        private fun checkTokenExpire(code: String) =
            (code == ServerStatusCode.TokenExpiration.code)
    }
}