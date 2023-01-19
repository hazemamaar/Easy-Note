package com.android.easynote.ui.fragments.home

import androidx.lifecycle.viewModelScope
import com.android.easynote.core.base.BaseViewModel
import com.android.easynote.core.extention.log
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.domain.DeleteNoteUseCase
import com.android.easynote.domain.GetAllNotesUseCase
import com.android.easynote.domain.SearchUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class HomeViewModel(private val GetAllUseCase: GetAllNotesUseCase,private val delete:DeleteNoteUseCase ,private val searchUseCase: SearchUseCase) : BaseViewModel<HomeAction>(){

    fun getAllData(){
        GetAllUseCase.invoke(coroutineScope = viewModelScope) { notesList ->
            produce(HomeAction.GetNoteList(notesList.sortedByDescending { it.pin }))
        }
        }
    fun delete(id:Int){
        delete.invoke(params = id, coroutineScope = viewModelScope){
            produce(HomeAction.OnRemove(it))
        }
    }
    fun onSearchQueryChanged(query:String) {
        searchUseCase.invoke(params = query, coroutineScope = viewModelScope){
            it.toString().log("hazem")
            produce(HomeAction.OnSearchByTitle(it.sortedByDescending { it.pin }))
        }


    }
}