package com.kakaovx.practice.networkmodule.network.constant

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}