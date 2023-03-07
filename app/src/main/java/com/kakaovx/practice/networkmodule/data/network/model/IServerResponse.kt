package com.kakaovx.practice.networkmodule.data.network.model

import com.kakaovx.practice.network.model.IHttpResponse

/**
 * @author Jinny (Hwang)
 *
 * 서버 요청의 API Response Class
 */
interface IServerResponse<T> : IHttpResponse<T> {
    val code: String
    val msg: String
    val data: T
}

// 테스트용
interface ITestServerResponse : IHttpResponse<Any> {
    val id: Int
}