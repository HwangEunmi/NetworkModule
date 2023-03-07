package com.kakaovx.practice.networkmodule.domain.usecase.base

/**
 * @author Jinny (Hwang)
 *
 * Combine용 Non Param UseCase
 */
abstract class CombineNonParamUseCase<R> {
    suspend operator fun invoke(): R {
        return execute()
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}