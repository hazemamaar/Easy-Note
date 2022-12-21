package com.android.easynote.data.local

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.android.easynote.data.entities.NoteDto

@Database(entities = [NoteDto::class], version = 3, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
 abstract val dao:NoteDao

}