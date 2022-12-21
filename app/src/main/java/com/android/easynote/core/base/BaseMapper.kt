package com.android.easynote.core.base

import com.android.easynote.data.entities.NoteDto

abstract class BaseMapper<in From,out To> {

    abstract fun map(from: From): To
    fun map(fromList: List<From>): List<To> = fromList.map { map(it) }

}