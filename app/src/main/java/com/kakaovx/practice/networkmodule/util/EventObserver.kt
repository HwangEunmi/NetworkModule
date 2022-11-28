package com.kakaovx.practice.networkmodule.util

import androidx.lifecycle.Observer
import com.kakaovx.practice.network.constant.StatusCode

class EventObserver(
    private val onEventUnhandledContent: (StatusCode) -> String,
    private val onEventResultCallback: (String) -> Unit
) : Observer<Event<StatusCode>> {

    override fun onChanged(event: Event<StatusCode>?) {
        event?.getContentIfNotHandled()?.let { effect ->
            val message = onEventUnhandledContent(effect)
            onEventResultCallback(message)
        }
    }
}