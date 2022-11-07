package com.kakaovx.practice.network


/**
 * @author Jinny
 * 요청이 성공할 경우 성공적인 응답을 처리하기 위해 실행되는 함수
 * @param onResult : 요청 성공시 ApiResponse.Success를 수신하는 리시버
 * @return The original [ApiResponse]
 */
inline fun <T> ApiResponse<T>.onSuccess(
    crossinline onResult: ApiResponse.Success<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 성공할 경우 성공적인 응답을 처리하기 위해 실행되는 일시 중단함수
 * @param onResult :요청 성공시 ApiResponse.Success를 수신하는 리시버
 * @return The original [ApiResponse]
 */
suspend inline fun <T> ApiResponse<T>.suspendOnSuccess(
    crossinline onResult: suspend ApiResponse.Success<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 실패할 경우 오류 응답을 처리하기 위해 실행되는 함수
 * @param onResult : 요청 실패시 ApiResponse.Failure.Error를 수신하는 리시버
 * @return The original [ApiResponse]
 */
inline fun <T> ApiResponse<T>.onError(
    crossinline onResult: ApiResponse.Failure.Error<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Failure.Error<T>) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 실패할 경우 오류 응답을 처리하기 위해 실행되는 일시 중단함수
 * @param onResult : 요청 실패시 ApiResponse.Failure.Error를 수신하는 리시버
 * @return The original [ApiResponse]
 */
suspend inline fun <T> ApiResponse<T>.suspendOnError(
    crossinline onResult: suspend ApiResponse.Failure.Error<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Failure.Error) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 예외를 가져올 경우 예외 응답을 처리하기 위해 실행되는 함수
 * @param onResult : 요청 실패시 ApiResponse.Failure.Exception을 수신하는 리시버
 * @return The original [ApiResponse]
 */
inline fun <T> ApiResponse<T>.onException(
    crossinline onResult: ApiResponse.Failure.Exception<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Failure.Exception) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 예외를 가져올 경우 예외 응답을 처리하기 위해 실행되는 일시 중단함수
 * @param onResult : 요청 실패시 ApiResponse.Failure.Exception을 수신하는 리시버
 * @return The original [ApiResponse]
 */
suspend inline fun <T> ApiResponse<T>.suspendOnException(
    crossinline onResult: suspend ApiResponse.Failure.Exception<T>.() -> Unit
): ApiResponse<T> {
    if (this is ApiResponse.Failure.Exception) {
        onResult(this)
    }
    return this
}