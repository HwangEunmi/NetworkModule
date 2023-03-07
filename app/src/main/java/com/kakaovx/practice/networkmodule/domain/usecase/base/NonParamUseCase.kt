package com.kakaovx.practice.networkmodule.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * @author Jinny (Hwang)
 *
 * Non Param UseCase
 */
abstract class NonParamUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(): R {
        return withContext(coroutineDispatcher) {
            execute()
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}