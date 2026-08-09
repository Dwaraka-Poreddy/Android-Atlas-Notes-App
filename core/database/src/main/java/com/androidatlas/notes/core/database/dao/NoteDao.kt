package com.androidatlas.notes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.androidatlas.notes.core.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE deleted = 0 ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id AND deleted = 0")
    fun getNoteById(id: String): Flow<NoteEntity?>

    @Query(
        "SELECT * FROM notes WHERE deleted = 0 AND (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%') ORDER BY updatedAt DESC"
    )
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateNote(note: NoteEntity)

    @Query("UPDATE notes SET deleted = 1, syncStatus = :syncStatus WHERE id = :id")
    suspend fun softDeleteNote(id: String, syncStatus: String)
}