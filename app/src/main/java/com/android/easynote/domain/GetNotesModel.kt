package com.android.easynote.domain

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class GetNotesModel (
    var id: Int = 0,
    var title: String? = null,
    var noteText: String? = null,
    var pin:Int =0,
    var lock:String ?=null,
    var color: String? = null,

        )