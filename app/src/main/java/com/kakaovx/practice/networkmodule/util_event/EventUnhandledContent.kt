package com.kakaovx.practice.networkmodule.util_event

import com.kakaovx.practice.network.constant.ErrorType
import com.kakaovx.practice.network.exceptions.NoContentException
import com.kakaovx.practice.networkmodule.data.network.constant.ServerStatusCode
import com.kakaovx.practice.networkmodule.data.network.exceptions.FailRenewTokenException
import com.kakaovx.practice.networkmodule.data.network.exceptions.NoConnectivityException
import com.kakaovx.practice.networkmodule.ui.constant.FailureState
import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType

private typealias SideEffectHandleCallback = (FailureState) -> SideEffectType

/**
 * @author Jinny (Hwang)
 *
 * Side Effect Handle Callback
 */
val EventUnhandledContent: SideEffectHandleCallback = object : SideEffectHandleCallback {
    override fun invoke(state: FailureState): SideEffectType {
        return when (state) {
            is FailureState.Error -> sideEffectOfError(
                errorType = state.errorType,
                errorCode = state.errorCode
            )
            is FailureState.Exception -> sideEffectOfExceptionByServer(
                state.exception
            )
        }
    }
}

private fun sideEffectOfError(errorType: ErrorType, errorCode: ServerStatusCode) =
    when (errorType) {
        ErrorType.TYPE_HTTP_ERROR -> SideEffectType.RETRY
        ErrorType.TYPE_SERVER_ERROR -> sideEffectOfErrorByServer(errorCode)
    }

/**
 * @author Jinny (Hwang)
 *
 * 서버 에러의 Side Effect 처리
 */
private fun sideEffectOfErrorByServer(errorCode: ServerStatusCode) =
    when (errorCode) {
        ServerStatusCode.HttpError -> SideEffectType.ERROR_POPUP
        ServerStatusCode.ResponseNotBaseResponse -> SideEffectType.ERROR_POPUP
        ServerStatusCode.TokenExpiration -> SideEffectType.MOVE_LOGIN
        else -> SideEffectType.ERROR_POPUP
    }

/**
 * @author Jinny (Hwang)
 *
 * 서버 Exception의 Side Effect 처리
 */
private fun sideEffectOfExceptionByServer(throwable: Throwable) =
    when (throwable) {
        is NoContentException -> SideEffectType.ERROR_POPUP
        is NoConnectivityException -> SideEffectType.RETRY
        is FailRenewTokenException -> SideEffectType.MOVE_LOGIN
        else -> SideEffectType.ERROR_POPUP
    }