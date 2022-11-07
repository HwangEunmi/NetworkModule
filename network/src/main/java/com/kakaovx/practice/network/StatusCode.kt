package com.kakaovx.practice.network

/**
 * @author Jinny
 * HTTP 응답 상태 코드 목록 클래스
 */
enum class StatusCode(val code: Int) {
    Unknown(0),
    OK(200),
    BadRequest(400),
    InternalServerError(500)
}