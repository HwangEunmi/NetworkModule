package com.kakaovx.practice.networkmodule.util_event

import androidx.lifecycle.Observer
import com.kakaovx.practice.networkmodule.ui.constant.FailureState
import com.kakaovx.practice.networkmodule.ui.constant.SideEffectType

class EventObserver(
    private val onEventUnhandledContent: (FailureState) -> SideEffectType,
    private val onEventResultCallback: (SideEffectType) -> Unit
) : Observer<Event<FailureState>> {

    override fun onChanged(event: Event<FailureState>?) {
        event?.getContentIfNotHandled()?.let { effect ->
            val message = onEventUnhandledContent(effect)
            onEventResultCallback(message)
        }
    }
}