package com.androidatlas.notes.core.network.dto

data class SyncRequestDto(
    val operations: List<SyncOperationDto>
)

data class SyncOperationDto(
    val localId: String,
    val operationType: String,
    val noteId: String?,
    val title: String?,
    val content: String?,
    val folderOrLabel: String?,
    val clientUpdatedAt: String
)
