package com.kakaovx.practice.networkmodule.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaovx.practice.networkmodule.data.network.LoadingHandleCallback
import com.kakaovx.practice.networkmodule.data.network.NetworkCallResultObserver
import com.kakaovx.practice.networkmodule.databinding.ActivityStateflowMainBinding
import com.kakaovx.practice.networkmodule.ui.constant.LoadingState
import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType
import com.kakaovx.practice.networkmodule.ui.view.base.BaseStateFlowViewModel
import com.kakaovx.practice.networkmodule.util_event.EventObserver
import com.kakaovx.practice.networkmodule.util_event.EventUnhandledContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainStateFlowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStateflowMainBinding
    private val viewModel: MainStateFlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStateflowMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.handleNetworkSideEffect()

        initObserver()

        binding.test.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.userInfoApiOperator.collect(
                        NetworkCallResultObserver(
                            loadingHandleCallback,
                            onSuccessCallback = {
                                Log.d("THEEND", "Call Success it: $it")
                            }
                        )
                    )
                }

                launch {
                    // viewModel.userCombineApiOperator.collect(
                    //     NetworkResultObserver(
                    //         loadingHandleCallback,
                    //         onSuccessCallback = {
                    //             Log.d("THEEND", "it2: $it")
                    //         }
                    //     )
                    // )
                }
            }
        }
    }

    private fun BaseStateFlowViewModel.handleNetworkSideEffect() {
        newObserveNetwork(EventObserver(EventUnhandledContent) { type ->
            Log.d("debug", "Call EventObserver: $type")
            when (type) {
                SideEffectType.ERROR_POPUP -> Log.d("THEEND", "에러팝업 호출하기")
                SideEffectType.RETRY -> {
                    Log.d("THEEND", "재시도 버튼 보여주기")
                    viewModel.requestUserInfo()
                }
                SideEffectType.MOVE_LOGIN -> Log.d("THEEND", "로그인 화면으로 이동하기")
            }
        })
    }

    private fun BaseStateFlowViewModel.newObserveNetwork(eventObserver: EventObserver) {
        sideEffect.observe(this@MainStateFlowActivity, eventObserver)
    }

    // TODO : 여러개 API 호출할 경우, 마지막 API 호출의 로딩 Gone일때 진짜 로딩바 Gone 되게 작업하기 (+ 에러 팝업도 포함)
    private val loadingHandleCallback = object : LoadingHandleCallback {
        override fun invoke(state: LoadingState.Loading) {
            when (state.isLoading) {
                true -> Log.d("THEEND", "로딩 VISIBLE")
                else -> Log.d("THEEND", "로딩 GONE")
            }
        }
    }
}