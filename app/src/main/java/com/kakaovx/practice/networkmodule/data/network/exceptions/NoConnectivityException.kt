package com.kakaovx.practice.networkmodule.data.network.exceptions

import java.io.IOException

/**
 * @author Jinny (Hwang)
 *
 * 인터넷 연결이 안되어있는 경우 호출시키는 Exception
 */
class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}