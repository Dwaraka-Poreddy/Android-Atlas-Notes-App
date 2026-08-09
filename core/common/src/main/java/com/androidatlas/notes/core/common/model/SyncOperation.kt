package com.androidatlas.notes.core.common.model

data class SyncOperation(
    val localId: String, // UUID generated on client, used to match request ↔ response
    val operationType: OperationType,
    val noteId: String?, // null for brand-new notes; set once server assigns an id
    val title: String?,
    val content: String?,
    val folderOrLabel: String?,
    val clientUpdatedAt: String // ISO 8601 timestamp when this change was made locally
)

enum class OperationType {
    UPSERT,
    DELETE
}