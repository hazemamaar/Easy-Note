package com.android.easynote.core.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow


interface Action
abstract class BaseViewModel<T:Action> : ViewModel() {
    private val nextAction = MutableSharedFlow<T>()
    val viewState: SharedFlow<T> = nextAction
}