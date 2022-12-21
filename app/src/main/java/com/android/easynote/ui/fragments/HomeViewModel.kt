package com.android.easynote.ui.fragments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.easynote.core.base.Action
import com.android.easynote.data.entities.NoteDto
import com.android.easynote.data.local.NoteDao
import com.android.easynote.domain.GetAllNotesUseCase
import com.android.easynote.domain.GetNotesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


sealed class HomeAction:Action{
    object OnCreateNote:HomeAction()
}

class HomeViewModel(private val useCase: GetAllNotesUseCase) : ViewModel() {
    private val nextAction  = MutableSharedFlow<List<GetNotesModel>>()
    val viewState: SharedFlow<List<GetNotesModel>> = nextAction
//    fun getAllData(){
//        viewModelScope.launch(Dispatchers.IO) {
//        val it= noteDao.getAllNotes()
//             Log.e("data", "getAllData: "+it.toString() )
//             nextAction.emit(it)
//         }
//    }
    fun getAllData(){
        useCase.invoke(coroutineScope = viewModelScope){
            viewModelScope.launch {
            Log.e("data", "getAllData: "+it.toString() )
             nextAction.emit(it)}
        }
    }
}