package com.kakaovx.practice.networkmodule.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach

// TODO : 재시도 구현해야되는데 이렇게 하기 보다는 StateFlow collect 다시 호출하는 방법 찾아보기 (flow를 copy하던지 해서)
internal inline fun <T> retryOperator(
    retryTrigger: RetryTrigger,
    crossinline flowProvider: () -> Flow<T>
) =
    retryTrigger.retryFlow

// internal inline fun <T> retryOperator(
//     retryTrigger: RetryTrigger,
//     crossinline flowProvider: () -> Flow<T>
// ) =
//     retryTrigger.retryFlow.filter { it == RetryTrigger.State.RETRY }
//         .flatMapConcat { flowProvider() }
//         .onEach { retryTrigger.retryFlow.value = RetryTrigger.State.IDLE }

class RetryTrigger {
    enum class State { RETRY, IDLE }

    val retryFlow = MutableStateFlow(State.RETRY)

    fun retry() {
        retryFlow.value = State.RETRY
    }
}

