package com.androidatlas.notes.feature.noteeditor

import com.androidatlas.notes.core.database.entity.NoteEntity
import com.androidatlas.notes.core.database.entity.SyncOperationEntity
import com.androidatlas.notes.core.database.entity.SyncStatus
import com.androidatlas.notes.core.database.repository.NoteRepository
import com.androidatlas.notes.core.sync.processor.SyncQueueProcessor
import android.util.Log
import java.time.Instant
import java.util.UUID

class SaveNoteUseCase(
    private val noteRepository: NoteRepository,
    private val syncQueueProcessor: SyncQueueProcessor
) {
    companion object {
        private const val TAG = "SaveNoteUseCase"
    }

    suspend fun execute(
        id: String? = null,
        title: String,
        content: String,
        folderOrLabel: String? = null
    ) {
        val noteId = id ?: UUID.randomUUID().toString()
        val isNew = id == null
        val now = Instant.now().toString()
        Log.d(TAG, "execute() — ${if (isNew) "NEW" else "UPDATE"} noteId=$noteId, title='$title'")

        val noteEntity = NoteEntity(
            id = noteId,
            title = title,
            content = content,
            folderOrLabel = folderOrLabel,
            updatedAt = now,
            deleted = false,
            syncStatus = SyncStatus.PENDING
        )
        noteRepository.saveNote(noteEntity)
        Log.d(TAG, "execute() — saved to local DB")

        val syncOperation = SyncOperationEntity(
            localId = UUID.randomUUID().toString(),
            operationType = "UPSERT",
            noteId = noteId,
            title = title,
            content = content,
            folderOrLabel = folderOrLabel,
            clientUpdatedAt = now,
            status = "PENDING"
        )
        syncQueueProcessor.enqueue(syncOperation)
        Log.d(TAG, "execute() — enqueued sync operation")
    }
}
