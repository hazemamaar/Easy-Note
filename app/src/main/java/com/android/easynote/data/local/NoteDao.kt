package com.android.easynote.data.local

import androidx.room.*
import androidx.room.Dao
import com.android.easynote.data.entities.NoteDto
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id DESC")
     fun getAllNotes(): Flow<List<NoteDto>>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getSpecificNote(id: Int): NoteDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note: NoteDto) : Long

    @Delete
    suspend fun deleteNotes(note: NoteDto)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteSpecificNote(id: Int) :Int

    @Update
    suspend fun updateNotes(note: NoteDto) :Int

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :searchQuery || '%' ")
    fun searchByTitleOrDescription(searchQuery: String): Flow<List<NoteDto>>
}