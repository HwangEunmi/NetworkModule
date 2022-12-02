package com.kakaovx.practice.networkmodule.util

suspend fun <T : Any, R : Any> whenAllNotNull(vararg options: T?, block: suspend (List<T>) -> R, notBlock: suspend () -> Unit) {
    if (options.all { it != null }) {
        block(options.filterNotNull())
    } else {
        notBlock()
    }
}