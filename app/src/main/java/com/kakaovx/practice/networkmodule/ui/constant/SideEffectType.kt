package com.kakaovx.practice.networkmodule.ui.constant

import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType.ERROR_POPUP
import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType.MOVE_LOGIN
import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType.RETRY

/**
 * @author Jinny (Hwang)
 *
 * Error/Exception인 경우의 SideEffect 코드 목록
 *
 * [RETRY]는 통신 실패이면서 재시도가 필요한 경우
 * [ERROR_POPUP]는 통신 실패이면서 에러팝업 호출이 필요한 경우
 * [MOVE_LOGIN]는 통신 실패이면서 로그인화면 이동이 필요한 경우
 */
enum class SideEffectType {
    RETRY,
    ERROR_POPUP,
    MOVE_LOGIN
}