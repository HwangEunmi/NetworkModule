package com.kakaovx.practice.networkmodule.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class NonParamUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(): R {
        return withContext(coroutineDispatcher) {
            execute()
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}