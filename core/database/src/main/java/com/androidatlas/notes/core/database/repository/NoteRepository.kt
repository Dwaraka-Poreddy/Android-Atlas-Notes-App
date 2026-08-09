package com.androidatlas.notes.core.database.repository

import com.androidatlas.notes.core.common.model.Note
import com.androidatlas.notes.core.database.dao.NoteDao
import com.androidatlas.notes.core.database.entity.NoteEntity
import com.androidatlas.notes.core.database.mapper.NoteEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(
    private val noteDao: NoteDao,
    private val noteEntityMapper: NoteEntityMapper
) {
    fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes().map { entities ->
            entities.map { noteEntityMapper.toDomain(it) }
        }
    }

    fun getNote(id: String): Flow<Note?> {
        return noteDao.getNoteById(id).map { entity ->
            entity?.let { noteEntityMapper.toDomain(it) }
        }
    }

    fun searchNotes(query: String): Flow<List<Note>> {
        return noteDao.searchNotes(query).map { entities ->
            entities.map { noteEntityMapper.toDomain(it) }
        }
    }

    suspend fun saveNote(note: NoteEntity) {
        noteDao.insertOrUpdateNote(note)
    }

    suspend fun deleteNote(id: String) {
        noteDao.softDeleteNote(id, "PENDING")
    }
}
