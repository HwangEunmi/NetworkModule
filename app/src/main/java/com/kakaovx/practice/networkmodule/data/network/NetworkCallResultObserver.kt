package com.kakaovx.practice.networkmodule.data.network

import com.kakaovx.practice.networkmodule.ui.constant.LoadingState
import com.kakaovx.practice.networkmodule.ui.constant.State
import com.kakaovx.practice.networkmodule.ui.constant.SuccessState
import kotlinx.coroutines.flow.FlowCollector

/**
 * @author Jinny (Hwang)
 *
 * Network Api 요청 결과 Observer
 */
class NetworkCallResultObserver(
    private val onLoadingCallback: (LoadingState.Loading) -> Unit,
    private val onSuccessCallback: (data: Any) -> Unit
) : FlowCollector<State> {
    override suspend fun emit(state: State) {
        when (state) {
            is LoadingState.Loading ->
                onLoadingCallback(state)
            is SuccessState.Success<*> ->
                state.data?.let { onSuccessCallback(it) }
        }
    }
}
