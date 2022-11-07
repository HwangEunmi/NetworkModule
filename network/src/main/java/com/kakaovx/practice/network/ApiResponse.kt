package com.kakaovx.practice.network

import com.kakaovx.practice.network.exceptions.NoContentException
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * @author Jinny
 * Retrofit 호출에서 표준 응답을 구성하기 위한 인터페이스
 */
sealed class ApiResponse<out T> {
    /**
     * API 성공 응답 클래스
     *
     * @param response : OkHttp 요청의 Response
     *
     * @property statusCode : HTTP 응답 상태 코드
     * @property headers : HTTP 메시지의 헤더 필드
     * @property data : 성공시의 데이터
     */
    data class Success<T>(val response: Response<T>) : ApiResponse<T>() {
        val statusCode: StatusCode = getStatusCodeFromResponse(response)
        val headers: Headers = response.headers()
        val data: T by lazy { response.body() ?: throw NoContentException(statusCode.code) }
        override fun toString(): String = "[ApiResponse.Success](data=$data)"
    }

    /**
     * OkHttp 요청 호출의 API 오류 응답 클래스
     * There are two subtypes: [ApiResponse.Failure.Error] and [ApiResponse.Failure.Exception]
     */
    sealed class Failure<T> : ApiResponse<T>() {
        /**
         * API 에러 응답 클래스
         *
         * @param response : OkHttp 요청의 Response
         *
         * @property statusCode : HTTP 응답 상태 코드
         * @property headers : HTTP 메시지의 헤더 필드
         * @property errorBody : 실패시의 에러값
         */
        data class Error<T>(val response: Response<T>) : Failure<T>() {
            val statusCode: StatusCode = getStatusCodeFromResponse(response)
            val headers: Headers = response.headers()
            val errorBody: ResponseBody? = response.errorBody()
            override fun toString(): String = errorBody?.toString()
                ?: "[ApiResponse.Failure.Error-$statusCode](errorResponse=$response)"
        }

        /**
         * API Exception 응답 클래스
         *
         * @param exception : An throwable exception
         * @property message : message from the Exception
         */
        data class Exception<T>(val exception: Throwable) : Failure<T>() {
            val message: String? = exception.localizedMessage
            override fun toString(): String = "[ApiResponse.Failure.Exception](message=$message)"
        }
    }

    /**
     * Response에서 상태코드를 반환한다.
     *
     * @param response : OkHttp 요청의 Response
     * @return 응답의 상태코드
     */
    fun <T> getStatusCodeFromResponse(response: Response<T>): StatusCode {
        return StatusCode.values().find { it.code == response.code() }
            ?: StatusCode.Unknown
    }
}