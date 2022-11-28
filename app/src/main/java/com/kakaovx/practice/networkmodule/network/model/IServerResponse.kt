package com.kakaovx.practice.networkmodule.network.model

import com.kakaovx.practice.network.model.IHttpResponse

interface IServerResponse<T> : IHttpResponse<T> {
    val code: String
    val msg: String
    val data: T
}

// 테스트용
interface ITestServerResponse : IHttpResponse<Any> {
    val id: Int
}