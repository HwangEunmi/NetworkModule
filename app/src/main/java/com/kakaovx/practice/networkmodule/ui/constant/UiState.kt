package com.kakaovx.practice.networkmodule.ui.constant

import com.kakaovx.practice.network.constant.ErrorType
import com.kakaovx.practice.networkmodule.network.constant.ServerStatusCode
import com.kakaovx.practice.networkmodule.ui.constant.FailureState.Error
import com.kakaovx.practice.networkmodule.ui.constant.FailureState.Exception
import com.kakaovx.practice.networkmodule.ui.constant.LoadingState.Loading
import com.kakaovx.practice.networkmodule.ui.constant.SuccessState.Success

/**
 * @author Jinny
 * 성공시의 UI State 클래스
 * 통신에 성공할 경우 [Success]에 데이터를 담아 전달한다.
 */
sealed class SuccessState<out T : Any> : State {
    data class Success<out T : Any>(
        val data: T?
    ) : SuccessState<T>() {
        val isEmpty = (data == null)
    }
}

/**
 * @author Jinny
 * 로딩 상태의 UI State 클래스
 * 통신의 시작/종료시 [Loading]에 상태값을 담아 전달한다.
 * * (true : Visible, false : Gone)
 */
sealed class LoadingState : State {
    data class Loading(
        val isLoading: Boolean = false
    ) : LoadingState()
}

/**
 * @author Jinny
 * Error/Exception의 UI State 클래스
 * 통신 에러인 경우 [Error]에 데이터를 담아 전달한다.
 * 통신 익셉션인 경우 [Exception]에 데이터를 담아 전달한다.
 */
sealed class FailureState : State {
    data class Error(
        val errorType: ErrorType, val errorCode: ServerStatusCode
    ) : FailureState()

    data class Exception(
        val exception: Throwable
    ) : FailureState()
}

/**
 * @author Jinny
 * 초기 상태 UI State 클래스
 */
sealed class IdleState : State {
    object Idle : IdleState()
}

interface State