package com.kakaovx.practice.network.constant

/**
 * @author Jinny
 * 통신 오류 타입 목록 클래스
 * [TYPE_HTTP_ERROR]은 HTTP 통신 에러다.
 * [TYPE_SERVER_ERROR]은 API 통신 에러다.
 */
enum class ErrorType {
    TYPE_HTTP_ERROR,
    TYPE_SERVER_ERROR
}