package com.android.easynote.ui.fragments.home

import androidx.lifecycle.viewModelScope
import com.android.easynote.core.base.BaseViewModel
import com.android.easynote.domain.DeleteNoteUseCase
import com.android.easynote.domain.GetAllNotesUseCase


class HomeViewModel(private val GetAllUseCase: GetAllNotesUseCase,private val delete:DeleteNoteUseCase) : BaseViewModel<HomeAction>(){

    fun getAllData(){
        GetAllUseCase.invoke(coroutineScope = viewModelScope){
           produce(HomeAction.GetNoteList(it))
        }
    }
    fun delete(id:Int){
        delete.invoke(params = id, coroutineScope = viewModelScope){
            produce(HomeAction.OnRemove(it))
        }
    }
}