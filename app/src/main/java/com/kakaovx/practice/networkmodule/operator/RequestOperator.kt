package com.kakaovx.practice.networkmodule.operator

import android.util.Log
import com.kakaovx.practice.networkmodule.network.SideEffectEventCallback
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.network.suspendOnError
import com.kakaovx.practice.networkmodule.network.suspendOnException
import com.kakaovx.practice.networkmodule.network.suspendOnSuccess
import com.kakaovx.practice.networkmodule.ui.constant.FailureState
import com.kakaovx.practice.networkmodule.ui.constant.LoadingState
import com.kakaovx.practice.networkmodule.ui.constant.State
import com.kakaovx.practice.networkmodule.ui.constant.SuccessState
import com.kakaovx.practice.networkmodule.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

internal suspend fun <T : Any> MutableStateFlow<State>.requestOperator(
    onApi: suspend () -> TestServerApiResponse<T>,
    onSideEffectEvent: SideEffectEventCallback
) {
    emit(LoadingState.Loading(false))
    onApi().suspendOperator(
        onSuccess = { success ->
            this.update { SuccessState.Success(success.data) }
        },
        onSideEffectEvent = { onSideEffectEvent(it) }
    )
}

suspend fun MutableStateFlow<State>.requestCombineOperator(
    onApi: suspend () -> Any,
    onSideEffectEvent: SideEffectEventCallback
) {
    onApi().combineSuspendOperator(
        onSuccess = { success ->
            this.update { SuccessState.Success(success.data) }
        },
        onSideEffectEvent = { onSideEffectEvent(it) })
}

private suspend inline fun <T> TestServerApiResponse<T>.suspendOperator(
    crossinline onSuccess: suspend (TestServerApiResponse.Success<T>) -> Unit,
    crossinline onSideEffectEvent: SideEffectEventCallback
) {
    this.suspendOnSuccess {
        Log.d("debug", this.toString())
        onSuccess(this)
    }.suspendOnError {
        Log.d("debug", this.toString())
        onSideEffectEvent(Event(FailureState.Error(this.errorType, this.errorCode)))
    }.suspendOnException {
        Log.d("debug", this.toString())
        onSideEffectEvent(Event(FailureState.Exception(this.exception)))
    }
}

fun StateFlow<State>.apiOperator(
    onSideEffectEvent: SideEffectEventCallback
) =
    this.onStart {
        emit(LoadingState.Loading(true))
    }.catch { throwable ->
        emit(LoadingState.Loading(false))
        onSideEffectEvent(Event(FailureState.Exception(throwable)))
    }

private suspend inline fun Any.combineSuspendOperator(
    crossinline onSuccess: suspend (TestServerApiResponse.Success<Any?>) -> Unit,
    crossinline onSideEffectEvent: SideEffectEventCallback
) {
    if (this is TestServerApiResponse<*>) {
        this.suspendOperator(
            onSuccess = { onSuccess(it) },
            onSideEffectEvent = { onSideEffectEvent(it) }
        )
    }
}