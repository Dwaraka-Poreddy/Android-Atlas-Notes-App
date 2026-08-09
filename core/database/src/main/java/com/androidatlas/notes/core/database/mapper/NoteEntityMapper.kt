package com.androidatlas.notes.core.database.mapper

import com.androidatlas.notes.core.common.model.Note
import com.androidatlas.notes.core.database.entity.NoteEntity

class NoteEntityMapper {
    fun toDomain(entity: NoteEntity): Note {
        return Note(
            id = entity.id,
            title = entity.title,
            content = entity.content,
            folderOrLabel = entity.folderOrLabel,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(domain: Note, syncStatus: String = "PENDING"): NoteEntity {
        return NoteEntity(
            id = domain.id,
            title = domain.title,
            content = domain.content,
            folderOrLabel = domain.folderOrLabel,
            updatedAt = domain.updatedAt,
            syncStatus = syncStatus.toSyncStatus()
        )
    }

    private fun String.toSyncStatus(): com.androidatlas.notes.core.database.entity.SyncStatus {
        return com.androidatlas.notes.core.database.entity.SyncStatus.valueOf(this)
    }
}