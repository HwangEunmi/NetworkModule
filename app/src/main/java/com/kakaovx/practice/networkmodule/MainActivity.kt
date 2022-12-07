package com.kakaovx.practice.networkmodule

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kakaovx.practice.networkmodule.base.BaseViewModel
import com.kakaovx.practice.networkmodule.util.EventObserver
import com.kakaovx.practice.networkmodule.util.EventUnhandledContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.handleNetworkSideEffect()

        initObserver()
    }

    private fun initObserver() {
        viewModel.userInfoData.observe(this) {
            Log.d("debug", "userInfoData => $it")
        }

        viewModel.userListData.observe(this) {
            Log.d("debug", "userListData => $it")
        }

        viewModel.userCombineData.observe(this) {
            Log.d("debug", "userCombineData => $it")
        }
    }

    // 각 Fragment가 아닌 Single Activity에서 콜백받기
    // 추후에 Dialog로 할때는 에러 팝업 딱 하나만 띄울 수 있도록 isShowing으로 관리하기
    private fun BaseViewModel.handleNetworkSideEffect() {
        newObserveNetwork(EventObserver(EventUnhandledContent) { result ->
            Log.d("debug", "Call Toast")
            Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
        })
    }

    private fun BaseViewModel.newObserveNetwork(eventObserver: EventObserver) {
        sideEffect.observe(this@MainActivity, eventObserver)
    }
}