package com.kakaovx.practice.networkmodule.datasource.local

import android.content.SharedPreferences

class TokenLocalDataSourceImpl(
    // private val preferences: SharedPreferences
) : TokenLocalDataSource {
    override var accessToken: String
        get() = "accessToken value"
        set(value) {
            value
        }

    // Preferences 사용시 아래처럼 작업해야 한다.
    // set(value){
    //    preference.accessToken = value
    // }
    // get() = preferences.accessToken
    // set(value) {}
}