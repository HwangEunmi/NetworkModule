package com.kakaovx.practice.networkmodule.network.exceptions

import java.io.IOException

class FailRenewTokenException : IOException() {
    override val message: String
        get() = "Fail Renew Token Exception"
}