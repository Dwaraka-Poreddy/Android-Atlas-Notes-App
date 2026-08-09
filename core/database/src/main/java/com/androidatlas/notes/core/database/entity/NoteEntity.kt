package com.androidatlas.notes.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey
    val id: String, // server-assigned UUID
    val title: String,
    val content: String,
    val folderOrLabel: String?,
    val updatedAt: String, // ISO 8601 timestamp from server
    val deleted: Boolean = false, // soft-delete flag
    val syncStatus: SyncStatus = SyncStatus.SYNCED
)
