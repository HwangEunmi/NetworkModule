package com.kakaovx.practice.networkmodule.usecase.base

abstract class CombineNonParamUseCase<R> {
    suspend operator fun invoke(): R {
        return execute()
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}