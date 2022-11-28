package com.kakaovx.practice.networkmodule.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class ParamUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(parameters: P): R {
        return withContext(coroutineDispatcher) {
            execute(parameters)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P): R
}