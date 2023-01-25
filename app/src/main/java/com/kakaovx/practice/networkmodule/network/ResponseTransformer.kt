package com.kakaovx.practice.networkmodule.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

// TODO : inline 함수랑 reified 사용 여부 생각해보기 (+ out 키워드도)

/**
 * @author Jinny
 * 요청이 성공할 경우 성공적인 응답을 처리하기 위해 실행되는 함수
 * @param onResult : 요청 성공시 ApiResponse.Success를 수신하는 리시버
 * @return The original [ServerApiResponse]
 */
internal inline fun <T: Any> ServerApiResponse<T>.onSuccess(
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
// private suspend inline fun <T: Any> ServerApiResponse<T>.suspendOnSuccess(
//     crossinline onResult: suspend ServerApiResponse.Success<T>.() -> Unit
// ): ServerApiResponse<T> {
//     if (this is ServerApiResponse.Success) {
//         onResult(this)
//     }
//     return this
// }

internal suspend inline fun <T> TestServerApiResponse<T>.suspendOnSuccess(
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
internal inline fun <T> ServerApiResponse<T>.onError(
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
// private suspend inline fun <T: Any> ServerApiResponse<T>.suspendOnError(
//     crossinline onResult: suspend ServerApiResponse.Failure.Error<T>.() -> Unit
// ): ServerApiResponse<T> {
//     if (this is ServerApiResponse.Failure.Error) {
//         onResult(this)
//     }
//     return this
// }

internal suspend inline fun <T> TestServerApiResponse<T>.suspendOnError(
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
internal inline fun <T> ServerApiResponse<T>.onException(
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
// private suspend inline fun <T: Any> ServerApiResponse<T>.suspendOnException(
//     crossinline onResult: suspend ServerApiResponse.Failure.Exception<T>.() -> Unit
// ): ServerApiResponse<T> {
//     if (this is ServerApiResponse.Failure.Exception) {
//         onResult(this)
//     }
//     return this
// }

internal suspend inline fun <T> TestServerApiResponse<T>.suspendOnException(
    crossinline onResult: suspend TestServerApiResponse.Failure.Exception<T>.() -> Unit
): TestServerApiResponse<T> {
    if (this is TestServerApiResponse.Failure.Exception) {
        onResult(this)
    }
    return this
}

/**
 * @author Jinny
 * 응답을 Flow 형태로 변환하는 함수
 * @return Flow 형태의 Response
 */
internal fun <T: Any> TestServerApiResponse<T>.toFlow(): Flow<TestServerApiResponse<T>> = flowOf(this)

/**
 * @author Jinny
 * 다수의 Flow API를 조합하여 결과를 리턴하는 함수
 *
 * @param apiFlows : Flow형태의 API 함수
 * @param onResult : 조합 결과 Response를 리턴하는 콜백함수
 * [Boolean]은 통신 결과를 나타낸다.
 * [Array]는 Response 묶음 배열을 나타낸다.
 */
internal suspend fun <T: Any> combines(
    vararg apiFlows: Flow<TestServerApiResponse<T>>,
    dispatcher: CoroutineDispatcher,
    onSuccessResult: suspend Array<TestServerApiResponse<T>>.() -> Unit,
    onFailResult: suspend TestServerApiResponse<T>.() -> Unit

) {
    combine(*apiFlows) { arrays: Array<TestServerApiResponse<T>> ->
        arrays.forEach { dataOfArray ->
            if (dataOfArray is TestServerApiResponse.Failure) {
                onFailResult(dataOfArray)
                return@combine
            }
        }

        onSuccessResult(arrays)
    }.flowOn(dispatcher)
        .collect()
}

/**
 * @author Jinny
 * 해당 API를 다시 호출하는 함수
 *
 * @param block : 호출해야 할 API
 */
internal suspend fun <T: Any> retryIO(
    block: suspend () -> T
): T {
    return block()
}