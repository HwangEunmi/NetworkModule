package com.kakaovx.practice.networkmodule.ui.view

import androidx.lifecycle.viewModelScope
import com.kakaovx.practice.networkmodule.network.SideEffectEventCallback
import com.kakaovx.practice.networkmodule.operator.apiOperator
import com.kakaovx.practice.networkmodule.operator.requestCombineOperator
import com.kakaovx.practice.networkmodule.operator.requestOperator
import com.kakaovx.practice.networkmodule.ui.constant.FailureState
import com.kakaovx.practice.networkmodule.ui.constant.IdleState
import com.kakaovx.practice.networkmodule.ui.constant.State
import com.kakaovx.practice.networkmodule.ui.view.base.BaseStateFlowViewModel
import com.kakaovx.practice.networkmodule.usecase.GetUserCombineUseCase
import com.kakaovx.practice.networkmodule.usecase.GetUserInfoUseCase
import com.kakaovx.practice.networkmodule.usecase.GetUserListUseCase
import com.kakaovx.practice.networkmodule.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainStateFlowViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserCombineUseCase: GetUserCombineUseCase
) : BaseStateFlowViewModel() {

    private val _userInfo = MutableStateFlow<State>(IdleState.Idle)
    val userInfo: StateFlow<State> = _userInfo.asStateFlow()

    private val _userCombine = MutableStateFlow<State>(IdleState.Idle)
    val userCombine: StateFlow<State> = _userCombine.asStateFlow()

    init {
        viewModelScope.launch {
            requestUserInfo()
            requestUserCombine()
        }
    }

    private val sideEffectEventCallback = object : SideEffectEventCallback {
        override fun invoke(event: Event<FailureState>) {
            _sideEffect.value = event
        }
    }

    val userInfoApiOperator = userInfo.apiOperator(sideEffectEventCallback)

    val userCombineApiOperator = userCombine.apiOperator(sideEffectEventCallback)

    // TODO : 제발 재시도!!!!!!
    private suspend fun requestUserInfo() = _userInfo.requestOperator(
        onApi = {
            getUserInfoUseCase(GetUserInfoUseCase.Params("octocat"))
        },
        onSideEffectEvent = sideEffectEventCallback,
    )

    private suspend fun requestUserCombine() = _userCombine.requestCombineOperator(
        onApi = {
            getUserCombineUseCase(GetUserCombineUseCase.Params("octocat"))
        },
        onSideEffectEvent = sideEffectEventCallback,
    )
}