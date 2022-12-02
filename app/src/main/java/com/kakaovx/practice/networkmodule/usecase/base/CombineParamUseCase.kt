package com.kakaovx.practice.networkmodule.usecase.base

abstract class CombineParamUseCase<in P> {
    suspend operator fun invoke(parameters: P, resultCallback: suspend (Any) -> Unit) {
        return execute(parameters, resultCallback)
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P, resultCallback: suspend (Any) -> Unit)
}