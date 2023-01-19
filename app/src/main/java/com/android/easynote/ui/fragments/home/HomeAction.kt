package com.android.easynote.ui.fragments.home

import com.android.easynote.core.base.Action
import com.android.easynote.data.entities.NoteDto

sealed class HomeAction: Action {
 data class GetNoteList(
     val noteList: List<NoteDto>
 ): HomeAction()
    data class OnSearchByTitle(val notes: List<NoteDto>) : HomeAction()
    data class OnRemove(val id : Int) : HomeAction()
}