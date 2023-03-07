package com.kakaovx.practice.networkmodule.data.datasource.local

/**
 * @author Jinny (Hwang)
 *
 * Token Local DataSource 인터페이스 구현체
 */
class TokenLocalDataSourceImpl(
    // private val preferences: PreferencesDataStore
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