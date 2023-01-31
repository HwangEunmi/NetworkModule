package com.kakaovx.practice.networkmodule.network

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

// internal inline fun <T> retryOperator(
//     retryTrigger: RetryTrigger,
//     crossinline flowProvider: () -> Flow<T>
// ) =
//     retryTrigger.retryFlow.filter { it == RetryTrigger.State.RETRY }
//         .flatMapConcat { flowProvider() }
//         .onEach { retryTrigger.retryFlow.value = RetryTrigger.State.IDLE }

// internal inline fun <T> retryOperator(
//     retryTrigger: RetryTrigger,
//     crossinline flowProvider: () -> Flow<T>
// )
//
// class RetryTrigger {
//
// }