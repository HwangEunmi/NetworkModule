package com.kakaovx.practice.networkmodule.ui.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakaovx.practice.networkmodule.ui.constant.FailureState
import com.kakaovx.practice.networkmodule.util.Event

open class BaseStateFlowViewModel : ViewModel() {
    protected val _sideEffect = MutableLiveData<Event<FailureState>>()
    val sideEffect: LiveData<Event<FailureState>> = _sideEffect
}