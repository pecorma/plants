package com.pecorma.plants.base

import com.pecorma.plants.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<S : UiState, E : UiEvent>(initialState: S) {
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    val timeCapsule: TimeCapsule<S> = TimeTravelCapsule { _state.tryEmit(it) }

    init {
        timeCapsule.addState(initialState)
    }

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    fun setState(newState: S) {
        val success = _state.tryEmit(newState)

        if (BuildConfig.DEBUG && success) {
            timeCapsule.addState(newState)
        }
    }

    abstract fun reduce(oldState: S, event: E)
}