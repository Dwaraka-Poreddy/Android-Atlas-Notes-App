package com.androidatlas.notes.core.network.dto

data class NoteDto(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val folderOrLabel: String?,
    val updatedAt: String,
    val deleted: Boolean
)
