package com.kakaovx.practice.networkmodule.data.network

import com.kakaovx.practice.networkmodule.ui.constant.FailureState
import com.kakaovx.practice.networkmodule.util_event.Event

typealias SideEffectEventCallback = (Event<FailureState>) -> Unit