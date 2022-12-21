package com.android.easynote.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


interface Action

abstract class BaseViewModel<T:Action> : ViewModel() {
    private val nextAction = MutableSharedFlow<T>()
    val viewState: SharedFlow<T> = nextAction

    fun produce(t: T) {
        viewModelScope.launch(Dispatchers.Main) {
            nextAction.emit(t)
        }
    }
}