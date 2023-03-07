package com.kakaovx.practice.networkmodule.data.network

/**
 * @author Jinny (Hwang)
 *
 * 값들이 모두 Null이 아닌지 확인하는 연산자
 *
 * @param options : Response값들
 * @param notNullBlock : 모두 Null이 아닌 경우의 Callback
 * @param nullBlock : Null이 하나라도 존재하는 경우의 Callback
 */
suspend fun <T : Any, R : Any> whenAllNotNull(vararg options: T?, notNullBlock: suspend (List<T>) -> R, nullBlock: suspend () -> Unit) {
    if (options.all { it != null }) {
        notNullBlock(options.filterNotNull())
    } else {
        nullBlock()
    }
}