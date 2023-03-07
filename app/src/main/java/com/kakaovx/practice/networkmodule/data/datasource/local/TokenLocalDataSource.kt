package com.kakaovx.practice.networkmodule.data.datasource.local

/**
 * @author Jinny (Hwang)
 *
 * Token Local DataSource 인터페이스
 */
interface TokenLocalDataSource {
    var accessToken: String
}
// 실제로는 SharedPreferences 또는 DataStore에서 값을 가져오거나 수정하기