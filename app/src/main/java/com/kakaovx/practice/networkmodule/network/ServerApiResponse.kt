package com.kakaovx.practice.networkmodule.network

import com.kakaovx.practice.network.*
import com.kakaovx.practice.network.constant.ApiErrorType
import com.kakaovx.practice.network.constant.StatusCode
import com.kakaovx.practice.network.model.IHttpResponse
import com.kakaovx.practice.networkmodule.network.constant.ServerStatusCode
import com.kakaovx.practice.networkmodule.network.model.IServerResponse

/**
 * @author Jinny
 * Retrofit 호출 후 서버 통신 표준 응답을 구성하기 위한 인터페이스
 */
open class ServerApiResponse<out T> {

    data class Success<T>(val response: IServerResponse<T>) : ServerApiResponse<T>() {
        val data = response.data
        override fun toString(): String = "[ServerApiResponse.Success](data=$data)"
    }

    sealed class Failure<T> : ServerApiResponse<T>() {
        data class Error<T>(
            val errorType: ApiErrorType = ApiErrorType.TYPE_HTTP_ERROR,
            val errorCode: StatusCode = ServerStatusCode.HttpError
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
         * HTTP 통신 성공/실패 구분값인 [HttpApiResponse]를 서버 성공/실패 구분값인 [ServerApiResponse]로 변환한다.
         */
        inline fun <T> of(
            // successCode: String = SERVER_SUCCESS_CODE,
            successCode: String? = null,
            crossinline f: () -> HttpApiResponse<T>
        ): ServerApiResponse<T> {
            val response = f()
            return when (response) {
                is HttpApiResponse.Success -> {
                    if (response.data !is IServerResponse<*>) {
                        Failure.Error<T>(
                            errorType = ApiErrorType.TYPE_SERVER_ERROR,
                            errorCode = getStatusCodeFromApiCode("XXXX")
                        )
                    }

                    if ((response.data as IServerResponse<*>).code == successCode) {
                        Success(response.data as IServerResponse<T>)
                    } else {
                        Failure.Error(
                            errorType = ApiErrorType.TYPE_SERVER_ERROR,
                            errorCode = getStatusCodeFromApiCode((response.data as IServerResponse<*>).code)
                        )
                    }
                }

                is HttpApiResponse.Failure.Error ->
                    Failure.Error(
                        errorType = ApiErrorType.TYPE_HTTP_ERROR,
                        errorCode = ServerStatusCode.HttpError
                    )

                is HttpApiResponse.Failure.Exception ->
                    Failure.Exception(response.exception)

                else -> throw TypeCastException("This type is not expected.")
            }
        }

        /**
         * Api 상태코드에서 서버 상태코드로 변환한다.
         *
         * @param code : Api 상태코드
         * @return 서버 상태코드
         */
        fun getStatusCodeFromApiCode(code: String): ServerStatusCode {
            return ServerStatusCode.values().find { it.code == code } ?: ServerStatusCode.HttpError
        }
    }
}