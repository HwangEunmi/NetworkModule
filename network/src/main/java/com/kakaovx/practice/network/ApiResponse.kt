package com.kakaovx.practice.network

import com.kakaovx.practice.network.exceptions.NoContentException
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response

sealed class ApiResponse<out T> {
    data class Success<T>(val response: Response<T>) : ApiResponse<T>() {
        val statusCode: StatusCode = getStatusCodeFromResponse(response)
        val headers: Headers = response.headers()
        val data: T by lazy { response.body() ?: throw NoContentException(statusCode.code) }
        override fun toString(): String = "[ApiResponse.Success](data=$data)"
    }

    data class Failure<T>(val response: Response<T>) : ApiResponse<T>() {
        val statusCode: StatusCode = getStatusCodeFromResponse(response)
        val headers: Headers = response.headers()
        val errorBody: ResponseBody? = response.errorBody()
        override fun toString(): String = errorBody?.toString()
            ?: "[ApiResponse.Failure.Error-$statusCode](errorResponse=$response)"
    }

    fun <T> getStatusCodeFromResponse(response: Response<T>): StatusCode {
        return StatusCode.values().find { it.code == response.code() }
            ?: StatusCode.Unknown
    }
}