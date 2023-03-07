package com.kakaovx.practice.networkmodule.data.model

import com.kakaovx.practice.networkmodule.data.network.model.ITestServerResponse

data class TestTokenResponse(
    val login: String,
    override val id: Int,
    val url: String
) : ITestServerResponse