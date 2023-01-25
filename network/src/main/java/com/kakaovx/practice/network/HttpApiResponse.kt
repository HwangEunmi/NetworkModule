package com.kakaovx.practice.network

import com.kakaovx.practice.network.constant.HTTP_SUCCESS_RANGE_CODE
import com.kakaovx.practice.network.constant.HttpStatusCode
import com.kakaovx.practice.network.exceptions.NoContentException
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * @author Jinny
 * Retrofit 호출에서 표준 응답을 구성하기 위한 인터페이스
 */
open class HttpApiResponse<out T> {
    /**
     * API 성공 응답 클래스
     *
     * @param response : OkHttp 요청의 Response
     *
     * @property statusCode : HTTP 응답 상태 코드
     * @property headers : HTTP 메시지의 헤더 필드
     * @property data : 성공시의 데이터
     */
    data class Success<T>(val response: Response<T>) : HttpApiResponse<T>() {
        val statusCode: HttpStatusCode = getStatusCodeFromResponse(response)
        val headers: Headers = response.headers()
        val data: T by lazy { response.body() ?: throw  NoContentException(statusCode.code) }
        override fun toString(): String = "[HttpApiResponse.Success](data=$data)"
    }

    /**
     * OkHttp 요청 호출의 API 오류 응답 클래스
     * There are two subtypes: [HttpApiResponse.Failure.Error] and [HttpApiResponse.Failure.Exception]
     */
    sealed class Failure<T> : HttpApiResponse<T>() {
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
            val statusCode: HttpStatusCode = getStatusCodeFromResponse(response)
            val headers: Headers = response.headers()
            val errorBody: ResponseBody? = response.errorBody()
            override fun toString(): String = errorBody?.toString()
                ?: "[HttpApiResponse.Failure.Error-$statusCode](errorResponse=$response)"
        }

        /**
         * API Exception 응답 클래스
         *
         * @param exception : An throwable exception
         * @property message : message from the Exception
         */
        data class Exception<T>(val exception: Throwable) : Failure<T>() {
            private val message: String? = exception.localizedMessage
            override fun toString(): String = "[HttpApiResponse.Failure.Exception](message=$message)"
        }
    }

    companion object {

        /**
         * @author Jinny
         * HttpApiResponse Factory
         *
         * @param successCodeRange : 응답이 성공 또는 실패했는지 확인하기 위한 성공범위 코드
         * @param f : 통신 결과 Response
         * 만약 [retrofit2.Response]에 오류가 없으면, [HttpApiResponse.Success]를 생성한다.
         * 만약 [retrofit2.Response]가 오류를 가지면, [HttpApiResponse.Failure.Error]를 생성한다.
         * 만약 [retrofit2.Response]에서 예외를 발생하면, [HttpApiResponse.Failure.Exception]를 생성한다.
         */
        inline fun <T: Any> of(
            successCodeRange: IntRange = HTTP_SUCCESS_RANGE_CODE,
            crossinline f: () -> Response<T>
        ): HttpApiResponse<T> = try {
            val response = f()
            if (response.raw().code() in successCodeRange) {
                Success(response)
            } else {
                Failure.Error(response)
            }
        } catch (e: Exception) {
            Failure.Exception(e)
        }

        /**
         * Response에서 상태코드를 반환한다.
         *
         * @param response : OkHttp 요청의 Response
         * @return 응답의 상태코드
         */
        fun <T> getStatusCodeFromResponse(response: Response<T>): HttpStatusCode {
            return HttpStatusCode.values().find { it.code == response.code() }
                ?: HttpStatusCode.Unknown
        }
    }
}