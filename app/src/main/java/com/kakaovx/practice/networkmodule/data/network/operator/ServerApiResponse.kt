package com.kakaovx.practice.networkmodule.data.network.operator

import com.kakaovx.practice.network.HttpApiResponse
import com.kakaovx.practice.network.constant.ErrorType
import com.kakaovx.practice.networkmodule.data.network.constant.ServerStatusCode
import com.kakaovx.practice.networkmodule.data.network.model.IServerResponse

/**
 * @author Jinny (Hwang)
 *
 * Retrofit 호출 후 서버 통신 표준 응답을 구성하기 위한 인터페이스
 */
open class ServerApiResponse<out T> {

    /**
     * 서버 요청의 API 성공 응답 클래스
     *
     * @param response : 서버 요청의 Response
     *
     * @property data : 성공시의 데이터
     */
    data class Success<T>(val response: IServerResponse<T>) : ServerApiResponse<T>() {
        val data = response.data
        override fun toString(): String = "[ServerApiResponse.Success](data=$data)"
    }

    /**
     * 서버 요청의 API 실패 응답 클래스
     * There are two subtypes: [ServerApiResponse.Failure.Error] and [ServerApiResponse.Failure.Exception].
     */
    sealed class Failure<T> : ServerApiResponse<T>() {
        /**
         * API 에러 응답 클래스
         *
         * @param errorCode : 실패시의 에러코드
         */
        data class Error<T>(
            val errorType: ErrorType = ErrorType.TYPE_HTTP_ERROR,
            val errorCode: ServerStatusCode = ServerStatusCode.HttpError
        ) : Failure<T>() {
            override fun toString(): String = "[ServerApiResponse.Failure.Error-$errorType](errorCode=$errorCode)"
        }

        /**
         * API Exception 응답 클래스
         *
         * @param exception : An throwable exception
         * @property message : message from the Exception
         */
        data class Exception<T>(val exception: Throwable) : Failure<T>() {
            private val message: String? = exception.localizedMessage
            override fun toString(): String = "[ServerApiResponse.Failure.Exception](message+$message)"
        }
    }

    companion object {

        /**
         * @author Jinny (Hwang)
         *
         * ServerApiResponse Factory
         *
         * @param successCode : 응답이 성공 또는 실패했는지 확인하기 위한 성공범위 코드
         * @param f : HttpApiResponse로 변환된 Response
         *
         * HTTP 통신 성공/실패 구분값인 [HttpApiResponse]를 서버 성공/실패 구분값인 [ServerApiResponse]로 변환한다.
         */
        inline fun <T : Any> of(
            // successCode: String = SERVER_SUCCESS_CODE,
            successCode: String? = null,
            crossinline f: () -> HttpApiResponse<T>
        ): ServerApiResponse<T> {
            val response = f()
            return when (response) {
                is HttpApiResponse.Success -> {
                    if (response.data !is IServerResponse<*>) {
                        Failure.Error<T>(
                            errorType = ErrorType.TYPE_SERVER_ERROR,
                            errorCode = getStatusCodeFromApiCode("XXXX")
                        )
                    }

                    if ((response.data as IServerResponse<*>).code == successCode) {
                        Success(response.data as IServerResponse<T>)
                    } else {
                        Failure.Error(
                            errorType = ErrorType.TYPE_SERVER_ERROR,
                            errorCode = getStatusCodeFromApiCode((response.data as IServerResponse<*>).code)
                        )
                    }
                }

                is HttpApiResponse.Failure.Error ->
                    Failure.Error(
                        errorType = ErrorType.TYPE_HTTP_ERROR,
                        errorCode = ServerStatusCode.HttpError
                    )

                is HttpApiResponse.Failure.Exception ->
                    Failure.Exception(response.exception)

                else -> throw TypeCastException("This type is not expected.")
            }
        }

        /**
         * @author Jinny (Hwang)
         *
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