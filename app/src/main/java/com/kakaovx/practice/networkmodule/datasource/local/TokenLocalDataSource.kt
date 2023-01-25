package com.kakaovx.practice.networkmodule.datasource.local

// 실제로는 SharedPreferences 또는 DataStore에서 값을 가져오거나 수정하기
interface TokenLocalDataSource {
    var accessToken: String
}