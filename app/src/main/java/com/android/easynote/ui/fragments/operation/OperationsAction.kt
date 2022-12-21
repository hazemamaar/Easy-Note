package com.android.easynote.ui.fragments.operation

import com.android.easynote.core.base.Action
import com.android.easynote.data.entities.NoteDto

sealed class OperationsAction : Action {
    data class CreateNote(val insertId:Long):OperationsAction()
    data class EditNote(val editId:Int):OperationsAction()

}