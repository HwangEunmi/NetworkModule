package com.kakaovx.practice.networkmodule.model

import com.kakaovx.practice.networkmodule.network.model.ITestServerResponse

data class TestTokenResponse(
    val login: String,
    override val id: Int,
    val url: String
) : ITestServerResponse