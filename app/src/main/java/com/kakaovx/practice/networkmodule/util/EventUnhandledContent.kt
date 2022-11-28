package com.kakaovx.practice.networkmodule.util

import com.kakaovx.practice.network.constant.ExceptionStatusCode
import com.kakaovx.practice.network.constant.StatusCode
import com.kakaovx.practice.networkmodule.network.constant.ServerStatusCode

typealias SideEffectHandleCallback = (StatusCode) -> String

val EventUnhandledContent: SideEffectHandleCallback = object : SideEffectHandleCallback {
    override fun invoke(code: StatusCode): String {
        return when (code) {
            ServerStatusCode.HttpError -> "HttpError: $code"
            ServerStatusCode.ResponseNotBaseResponse -> "ResponseNotBaseResponse: $code"
            ExceptionStatusCode.CoroutineException -> "Exception: $code"
            else -> "Exception: $code"
        }
    }
}