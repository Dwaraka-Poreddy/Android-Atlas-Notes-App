package com.androidatlas.notes.core.common.model

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val folderOrLabel: String?,
    val updatedAt: String // ISO 8601 timestamp from server
)