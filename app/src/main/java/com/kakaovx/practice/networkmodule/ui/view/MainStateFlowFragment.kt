package com.kakaovx.practice.networkmodule.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaovx.practice.networkmodule.databinding.ActivityStateflowMainBinding
import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType
import com.kakaovx.practice.networkmodule.ui.view.base.BaseStateFlowViewModel
import com.kakaovx.practice.networkmodule.util.EventObserver
import com.kakaovx.practice.networkmodule.util.EventUnhandledContent
import kotlinx.coroutines.launch

class MainStateFlowFragment : Fragment() {
    private lateinit var binding: ActivityStateflowMainBinding
    private val viewModel: MainStateFlowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ActivityStateflowMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.handleNetworkSideEffect()
        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userInfo.collect {
                    Log.d("debug", "userInfo => $it")
                }
            }
        }
    }

    // 각 Fragment가 아닌 Single Activity에서 콜백받기
    // 추후에 Dialog로 할때는 에러 팝업 딱 하나만 띄울 수 있도록 isShowing으로 관리하기
    private fun BaseStateFlowViewModel.handleNetworkSideEffect() {
        newObserveNetwork(EventObserver(EventUnhandledContent) { type ->
            Log.d("debug", "Call EventObserver: $type")
            when (type) {
                SideEffectType.ERROR_POPUP -> Log.d("THEEND", "에러팝업 호출하기")
                SideEffectType.RETRY -> Log.d("THEEND", "재시도 버튼 보여주기")
                SideEffectType.MOVE_LOGIN -> Log.d("THEEND", "로그인 화면으로 이동하기")
            }
        })
    }

    private fun BaseStateFlowViewModel.newObserveNetwork(eventObserver: EventObserver) =
        sideEffect.observe(viewLifecycleOwner, eventObserver)

    // (필요시 사용)
    // private fun cancelObservingSideEffect() = viewModel.sideEffect.removeObservers(this)
}