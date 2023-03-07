package com.kakaovx.practice.networkmodule.data.network.constant

/**
 * @author Jinny (Hwang)
 *
 * 서버 API 통신 응답 상태 코드
 */
enum class ServerStatusCode(val code: String) {
    Success("E000"),
    HttpError(""),
    TokenExpiration("EEEE"), // 토큰 만료 코드
    ResponseNotBaseResponse("InvalidType") // Response의 Base 타입이 잘못된 경우
}