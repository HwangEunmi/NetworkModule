package com.kakaovx.practice.networkmodule

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kakaovx.practice.networkmodule.base.BaseViewModel
import com.kakaovx.practice.networkmodule.model.CombineUserInfoResponse
import com.kakaovx.practice.networkmodule.model.TestUserInfoResponse
import com.kakaovx.practice.networkmodule.model.TestUserListResponse
import com.kakaovx.practice.networkmodule.network.TestServerApiResponse
import com.kakaovx.practice.networkmodule.usecase.GetUserCombineUseCase
import com.kakaovx.practice.networkmodule.usecase.GetUserInfoUseCase
import com.kakaovx.practice.networkmodule.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserCombineUseCase: GetUserCombineUseCase
) : BaseViewModel() {

    val userInfoData: LiveData<TestUserInfoResponse> = liveData(context) {
        getUserInfoUseCase(GetUserInfoUseCase.Params("octocat"))
            .suspendOperator {
                emit(it.data)
            }
    }

    val userListData: LiveData<List<TestUserListResponse>> = liveData(context) {
        getUserListUseCase().suspendOperator {
            emit(it.data)
        }
    }

    val userCombineData: LiveData<CombineUserInfoResponse> = liveData(context) {
        getUserCombineUseCase(GetUserCombineUseCase.Params("octocat"))
            .combineSuspendOperator {
                emit(it.data as CombineUserInfoResponse)
            }
    }
}