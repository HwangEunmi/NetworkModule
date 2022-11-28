package com.kakaovx.practice.networkmodule.network.constant

import com.kakaovx.practice.network.constant.StatusCode

/**
 * @author Jinny
 * API 응답 상태 코드 목록 클래스
 */
enum class ServerStatusCode(val code: String) : StatusCode {
    Success("E000"),

    HttpError(""), // 이 값을 받으면 '일시적인 오류'팝업 등 하나로 통일 (통신 오류니까)
    ResponseNotBaseResponse("XXXX") // Response가 BaseResponse를 상속받지 않은 경우
}