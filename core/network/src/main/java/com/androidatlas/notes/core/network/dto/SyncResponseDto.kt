package com.androidatlas.notes.core.network.dto

data class SyncResponseDto(
    val results: List<SyncResultDto>
)

data class SyncResultDto(
    val localId: String,
    val status: String, // "applied", "conflict", or "error"
    val note: NoteDto?,
    val message: String?
)
