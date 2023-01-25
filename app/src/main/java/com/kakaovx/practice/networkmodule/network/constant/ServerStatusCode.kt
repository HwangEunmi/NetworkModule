package com.kakaovx.practice.networkmodule.network.constant

/**
 * @author Jinny
 * API 응답 상태 코드 목록 클래스
 */
enum class ServerStatusCode(val code: String) {
    Success("E000"),

    HttpError(""), // 이 값을 받으면 '일시적인 오류'팝업 등 하나로 통일 (통신 오류니까)
    TokenExpiration("EEEE"), // 토큰 만료 코드
    ResponseNotBaseResponse("XXXX") // Response가 BaseResponse를 상속받지 않은 경우
}