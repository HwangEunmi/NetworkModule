package com.kakaovx.practice.networkmodule.ui.constant

import com.kakaovx.practice.network.constant.ErrorType
import com.kakaovx.practice.networkmodule.data.network.constant.ServerStatusCode
import com.kakaovx.practice.networkmodule.ui.constant.FailureState.Error
import com.kakaovx.practice.networkmodule.ui.constant.FailureState.Exception
import com.kakaovx.practice.networkmodule.ui.constant.LoadingState.Loading
import com.kakaovx.practice.networkmodule.ui.constant.SuccessState.Success

/**
 * @author Jinny (Hwang)
 *
 * 통신 성공할 경우의 UI State
 * [Success]에 데이터를 담아 전달한다.
 */
sealed class SuccessState<out T : Any> : State {
    data class Success<out T : Any>(
        val data: T?
    ) : SuccessState<T>() {
        val isEmpty = (data == null)
    }
}

/**
 * @author Jinny (Hwang)
 *
 * 로딩 상태인 경우의 UI State
 * [Loading]에 상태값을 담아 전달한다.
 */
sealed class LoadingState : State {
    data class Loading(
        val isLoading: Boolean = false
    ) : LoadingState()
}

/**
 * @author Jinny (Hwang)
 *
 * 통신 실패인 경우의 UI State
 *
 * 통신 에러인 경우, [Error]에 데이터를 담아 전달한다.
 * 통신 Exception인 경우, [Exception]에 데이터를 담아 전달한다.
 */
sealed class FailureState : State {
    data class Error(
        val errorType: ErrorType,
        val errorCode: ServerStatusCode
    ) : FailureState()

    data class Exception(
        val exception: Throwable
    ) : FailureState()
}

/**
 * @author Jinny (Hwang)
 *
 * Idle 상태인 경우의 UI State
 */
sealed class IdleState : State {
    object Idle : IdleState()
}

interface State