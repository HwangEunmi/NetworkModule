package com.kakaovx.practice.networkmodule.ui.view

import androidx.lifecycle.viewModelScope
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.ui.view.base.BaseStateFlowViewModel
import com.kakaovx.practice.networkmodule.usecase.GetUserCombineUseCase
import com.kakaovx.practice.networkmodule.usecase.GetUserInfoUseCase
import com.kakaovx.practice.networkmodule.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainStateFlowViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserCombineUseCase: GetUserCombineUseCase
) : BaseStateFlowViewModel() {

    val userInfo = requestOperator {
        getUserInfoUseCase(GetUserInfoUseCase.Params("octocat"))
    }

    val _test = MutableStateFlow<TestServerApiResponse<TestUserInfoResponse>?>(null)
    val test: StateFlow<TestServerApiResponse<TestUserInfoResponse>?> = _test.asStateFlow()

    val userInfoTest = requestUserInfo()

    private fun requestUserInfo() = requestOperator {
        getUserInfoUseCase(GetUserInfoUseCase.Params("octocat"))
    }

    fun testCall() {
        viewModelScope.launch {
            val value = getUserInfoUseCase(GetUserInfoUseCase.Params("octocat"))
            _test.update { value }
        }
        // _test
        //     // .map { count -> count > 0 }
        //     .map { !it.isRefresh }
        //     .distinctUntilChanged()
        //     .onEach { isActive ->
        //         if (isActive) {
        //            Log.d("THEEND", "Real Call")
        //                TestModel(true, 1)
        //         } else {
        //             Log.d("THEEND", "Not Call")
        //         }
        //     }.launchIn(viewModelScope)
    }
    init {
        testCall()
    }

    data class TestModel(
        val isRefresh: Boolean = false,
        var num: Int = 0
    )
    // TODO : 제발 재시도!!!!!!
    val userList = callOperator {
        getUserListUseCase()
    }

    val userCombine = requestCombineOperator {
        getUserCombineUseCase(GetUserCombineUseCase.Params("octocat"))
    }
}