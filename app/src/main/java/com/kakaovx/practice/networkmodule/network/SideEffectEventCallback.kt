package com.kakaovx.practice.networkmodule.network

import com.kakaovx.practice.networkmodule.ui.constant.FailureState
import com.kakaovx.practice.networkmodule.util.Event

typealias SideEffectEventCallback = (Event<FailureState>) -> Unit