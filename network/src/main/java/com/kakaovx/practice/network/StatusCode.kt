package com.kakaovx.practice.network

enum class StatusCode(val code: Int) {
    Unknown(0),
    OK(200),
    BadRequest(400),
    InternalServerError(500)
}