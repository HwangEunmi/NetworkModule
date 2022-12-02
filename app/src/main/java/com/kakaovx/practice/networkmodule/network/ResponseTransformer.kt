package com.kakaovx.practice.networkmodule.network

/**
 * @author Jinny
 * 요청이 성공할 경우 성공적인 응답을 처리하기 위해 실행되는 함수
 * @param onResult : 요청 성공시 ApiResponse.Success를 수신하는 리시버
 * @return The original [ServerApiResponse]
 */
inline fun <T> ServerApiResponse<T>.onSuccess(
    crossinline onResult: ServerApiResponse.Success<T>.() -> Unit
): ServerApiResponse<T> {
    if (this is ServerApiResponse.Success) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 성공할 경우 성공적인 응답을 처리하기 위해 실행되는 일시 중단함수
 * @param onResult :요청 성공시 ApiResponse.Success를 수신하는 리시버
 * @return The original [ServerApiResponse]
 */
// private suspend inline fun <T> ServerApiResponse<T>.suspendOnSuccess(
//     crossinline onResult: suspend ServerApiResponse.Success<T>.() -> Unit
// ): ServerApiResponse<T> {
//     if (this is ServerApiResponse.Success) {
//         onResult(this)
//     }
//     return this
// }

suspend inline fun <T> TestServerApiResponse<T>.suspendOnSuccess(
    crossinline onResult: suspend TestServerApiResponse.Success<T>.() -> Unit
): TestServerApiResponse<T> {
    if (this is TestServerApiResponse.Success) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 실패할 경우 오류 응답을 처리하기 위해 실행되는 함수
 * @param onResult : 요청 실패시 ApiResponse.Failure.Error를 수신하는 리시버
 * @return The original [ServerApiResponse]
 */
inline fun <T> ServerApiResponse<T>.onError(
    crossinline onResult: ServerApiResponse.Failure.Error<T>.() -> Unit
): ServerApiResponse<T> {
    if (this is ServerApiResponse.Failure.Error) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 실패할 경우 오류 응답을 처리하기 위해 실행되는 일시 중단함수
 * @param onResult : 요청 실패시 ApiResponse.Failure.Error를 수신하는 리시버
 * @return The original [ServerApiResponse]
 */
// private suspend inline fun <T> ServerApiResponse<T>.suspendOnError(
//     crossinline onResult: suspend ServerApiResponse.Failure.Error<T>.() -> Unit
// ): ServerApiResponse<T> {
//     if (this is ServerApiResponse.Failure.Error) {
//         onResult(this)
//     }
//     return this
// }

suspend inline fun <T> TestServerApiResponse<T>.suspendOnError(
    crossinline onResult: suspend TestServerApiResponse.Failure.Error<T>.() -> Unit
): TestServerApiResponse<T> {
    if (this is TestServerApiResponse.Failure.Error) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 예외를 가져올 경우 예외 응답을 처리하기 위해 실행되는 함수
 * @param onResult : 요청 실패시 ApiResponse.Failure.Exception을 수신하는 리시버
 * @return The original [ServerApiResponse]
 */
inline fun <T> ServerApiResponse<T>.onException(
    crossinline onResult: ServerApiResponse.Failure.Exception<T>.() -> Unit
): ServerApiResponse<T> {
    if (this is ServerApiResponse.Failure.Exception) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 요청이 예외를 가져올 경우 예외 응답을 처리하기 위해 실행되는 일시 중단함수
 * @param onResult : 요청 실패시 ApiResponse.Failure.Exception을 수신하는 리시버
 * @return The original [ServerApiResponse]
 */
// private suspend inline fun <T> ServerApiResponse<T>.suspendOnException(
//     crossinline onResult: suspend ServerApiResponse.Failure.Exception<T>.() -> Unit
// ): ServerApiResponse<T> {
//     if (this is ServerApiResponse.Failure.Exception) {
//         onResult(this)
//     }
//     return this
// }

suspend inline fun <T> TestServerApiResponse<T>.suspendOnException(
    crossinline onResult: suspend TestServerApiResponse.Failure.Exception<T>.() -> Unit
): TestServerApiResponse<T> {
    if (this is TestServerApiResponse.Failure.Exception) {
        onResult(this)
    }
    return this
}/**
 * @author Jinny
 * 응답을 Flow 형태로 변환하는 함수
 * @return Flow 형태의 Response
 */
fun <T> TestServerApiResponse<T>.toFlow(): Flow<TestServerApiResponse<T>> = flowOf(this)
