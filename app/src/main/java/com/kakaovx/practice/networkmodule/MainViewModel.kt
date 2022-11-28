package com.kakaovx.practice.networkmodule

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kakaovx.practice.networkmodule.base.BaseViewModel
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.usecase.GetUserInfoUseCase
import com.kakaovx.practice.networkmodule.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserListUseCase: GetUserListUseCase
) : BaseViewModel() {

    val userInfoData: LiveData<TestUserInfoResponse> = liveData(context) {
        getUserInfoUseCase(GetUserInfoUseCase.Params("octocat"))
            .suspendOperator {
                emit(it.data)
            }
    }

    val userListData: LiveData<List<TestUserInfoResponse>> = liveData(context) {
        getUserListUseCase().suspendOperator {
            emit(it.data)
        }
    }
}