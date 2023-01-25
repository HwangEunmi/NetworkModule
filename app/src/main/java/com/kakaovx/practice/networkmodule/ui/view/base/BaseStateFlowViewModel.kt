package com.kakaovx.practice.networkmodule.ui.view.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaovx.practice.networkmodule.network.RetryTrigger
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.network.retryOperator
import com.kakaovx.practice.networkmodule.network.suspendOnError
import com.kakaovx.practice.networkmodule.network.suspendOnException
import com.kakaovx.practice.networkmodule.network.suspendOnSuccess
import com.kakaovx.practice.networkmodule.ui.constant.FailureState
import com.kakaovx.practice.networkmodule.ui.constant.IdleState
import com.kakaovx.practice.networkmodule.ui.constant.LoadingState
import com.kakaovx.practice.networkmodule.ui.constant.State
import com.kakaovx.practice.networkmodule.ui.constant.SuccessState
import com.kakaovx.practice.networkmodule.util.Event
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

open class BaseStateFlowViewModel : ViewModel() {
    private val _sideEffect = MutableLiveData<Event<FailureState>>()
    val sideEffect: LiveData<Event<FailureState>> = _sideEffect

    private val retryTrigger by lazy {
        RetryTrigger()
    }

    protected fun <T : Any> callOperator(
        onApi: suspend () -> TestServerApiResponse<T>
    ) = retryOperator(retryTrigger) {
        requestOperator(onApi)
    }

    protected fun <T : Any> callCombinedOperator(
        onApi: suspend () -> Any
    ) = retryOperator(retryTrigger) {
        requestCombineOperator(onApi)
    }

    protected fun <T : Any> requestOperator(
        onApi: suspend () -> TestServerApiResponse<T>
    ) = apiOperator {
        onApi().suspendOperator {
            emit(SuccessState.Success(it.data))
        }
    }

    protected fun requestCombineOperator(
        onApi: suspend () -> Any
    ) = apiOperator {
        onApi().combineSuspendOperator {
            emit(SuccessState.Success(it.data))
        }
    }

    private inline fun apiOperator(
        crossinline onApiOperator: suspend FlowCollector<State>.() -> Unit
    ) = flow {
        onApiOperator()
    }.onStart {
        emit(LoadingState.Loading(true))
    }.onCompletion {
        emit(LoadingState.Loading(false))
    }.catch { throwable ->
        _sideEffect.value = Event(FailureState.Exception(throwable))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(0),
        initialValue = IdleState.Idle
    )

        .shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(0),
        replay = 1
    )

    private suspend inline fun <T> TestServerApiResponse<T>.suspendOperator(
        crossinline success: suspend (TestServerApiResponse.Success<T>) -> Unit
    ) {
        this.suspendOnSuccess {
            Log.d("debug", this.toString())
            success(this)
        }.suspendOnError {
            Log.d("debug", this.toString())
            _sideEffect.value = Event(FailureState.Error(this.errorType, this.errorCode))
        }.suspendOnException {
            Log.d("debug", this.toString())
            _sideEffect.value = Event(FailureState.Exception(this.exception))
        }
    }

    private suspend inline fun Any.combineSuspendOperator(
        crossinline callback: suspend (TestServerApiResponse.Success<Any?>) -> Unit
    ) {
        if (this is TestServerApiResponse<*>) {
            this.suspendOperator { success ->
                callback(success)
            }
        }
    }
}