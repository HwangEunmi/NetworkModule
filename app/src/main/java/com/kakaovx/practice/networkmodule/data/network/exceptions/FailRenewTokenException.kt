package com.kakaovx.practice.networkmodule.data.network.exceptions

import java.io.IOException

/**
 * @author Jinny (Hwang)
 *
 * Token 갱신에 실패할 경우 호출시키는 Exception
 */
class FailRenewTokenException : IOException() {
    override val message: String
        get() = "Fail Renew Token Exception"
}