package com.kakaovx.practice.networkmodule.util

suspend fun <T : Any, R : Any> whenAllNotNull(vararg options: T?, notNullBlock: suspend (List<T>) -> R, nullBlock: suspend () -> Unit) {
    if (options.all { it != null }) {
        notNullBlock(options.filterNotNull())
    } else {
        nullBlock()
    }
}