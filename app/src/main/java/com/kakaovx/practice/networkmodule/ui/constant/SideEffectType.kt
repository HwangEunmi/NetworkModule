package com.kakaovx.practice.networkmodule.ui.constant

import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType.ERROR_POPUP
import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType.MOVE_LOGIN
import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType.RETRY

/**
 * @author Jinny
 * Error/Exception인 경우의 SideEffect 코드 목록 클래스
 * - 통신에 실패할 경우 & 재시도가 필요한 경우 [RETRY]로 표현한다.
 * - 통신에 실패할 경우 & 에러팝업 호출이 필요한 경우 [ERROR_POPUP]로 표현한다.
 * - 통신에 실패할 경우 & 로그인화면 이동이 필요한 경우 [MOVE_LOGIN]로 표현한다.
 */
enum class SideEffectType {
    RETRY,
    ERROR_POPUP,
    MOVE_LOGIN
}

// UI State : Loading   ->  Side Effect : Show Progress
// UI State : Success   ->  Side Effect : Hide Progress(If Showing) + (Has Data / No Data)
// UI State : Error     ->  Side Effect : Hide Progress(If Showing) + (Error Popup / Retry / Move Login)
// UI State : Exception ->  Side Effect : Hide Progress(If Showing) + (Error Popup / Retry / Move Login)

// 전역에서 팝업 상태 다루기 (한 화면에 하나만 다룰 수 있도록)
// * 다음 화면으로 이동할때 팝업 또는 Progress 가 Show 인 경우  ->  Dismiss 시키기
// * 팝업 또는 Progress 를 호출할때 이미 Show 인지 확인하기  ->  (이미 Show 면 Show 안하기)

// TODO : Single Activity (Multi Fragment) 환경? 또는 Multi Activity 환경 이 나을지?
// https://github.com/DuartBreedt/Android-Navigation-in-a-Single-Activity-Multi-Module-App/blob/master/README.md
// Navigator Module
// MultiModuleMovie 프로젝트 참고하기 (core > navigation)

// TODO : UI State Modeling도 생각해보기

