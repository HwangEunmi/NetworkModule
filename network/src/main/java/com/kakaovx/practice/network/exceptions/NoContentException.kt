package com.kakaovx.practice.network.exceptions

class NoContentException constructor(
    val code: Int,
    override val message: String? =
        "The server request with the code($code) but no additional content to send in the response payload body."
) : Throwable(message)