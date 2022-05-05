package com.pecorma.plants.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<T : UiState, in E : UiEvent> : ViewModel() {
    abstract val state: Flow<T>
}
