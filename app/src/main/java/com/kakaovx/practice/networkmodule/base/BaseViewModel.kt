package com.kakaovx.practice.networkmodule.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakaovx.practice.network.constant.ExceptionStatusCode
import com.kakaovx.practice.network.constant.StatusCode
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.network.constant.ServerStatusCode
import com.kakaovx.practice.networkmodule.network.suspendOnError
import com.kakaovx.practice.networkmodule.network.suspendOnException
import com.kakaovx.practice.networkmodule.network.suspendOnSuccess
import com.kakaovx.practice.networkmodule.util.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel : ViewModel() {
    private val _sideEffect = MutableLiveData<Event<StatusCode>>()
    val sideEffect: LiveData<Event<StatusCode>> = _sideEffect

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("debug", "exceptionHandler: ${throwable.message.toString()}")
        _sideEffect.value = Event(ExceptionStatusCode.CoroutineException)
    }

    private val job = SupervisorJob()

    protected val context = Dispatchers.Main.immediate + job + exceptionHandler

    protected suspend fun <T> TestServerApiResponse<T>.suspendOperator(
        success: suspend (TestServerApiResponse.Success<T>) -> Unit
    ) {
        this.suspendOnSuccess {
            Log.d("debug", this.toString())
            success(this)
        }.suspendOnError {
            Log.d("debug", this.toString())
            _sideEffect.value = Event(this.errorCode)
        }.suspendOnException {
            Log.d("debug", this.toString())
            _sideEffect.value = Event(ServerStatusCode.HttpError)
        }
    }

    protected suspend inline fun Any.combineSuspendOperator(
        crossinline callback: suspend (TestServerApiResponse.Success<Any?>) -> Unit
    ) {
        if (this is TestServerApiResponse<*>) {
            this.suspendOperator { success ->
                callback(success)
            }
        }
    }
}