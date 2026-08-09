package com.androidatlas.notes.core.database.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "notes_fts")
@Fts4(contentEntity = NoteEntity::class)
data class NoteFts(
    @PrimaryKey
    val rowid: Int,
    val title: String,
    val content: String
)
