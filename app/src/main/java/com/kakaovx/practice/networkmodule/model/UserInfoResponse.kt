package com.kakaovx.practice.networkmodule.model

import com.kakaovx.practice.networkmodule.network.model.IServerResponse
import com.kakaovx.practice.networkmodule.network.model.ITestServerResponse

data class UserInfoResponse(
// val code: String = "E000", // 테스트용 (성공이라고 가정)
    override val code: String,
    override val msg: String,
    override val data: UserInfo

) : IServerResponse<UserInfoResponse.UserInfo> {
    data class UserInfo(
        val id: Int,
        val login: String,
        val avatar_url: String
    )
}

// Git API 테스트용 데이터
data class TestUserInfoResponse(
    val login: String,
    override val id: Int,
    val avatar_url: String
): ITestServerResponse