package com.androidatlas.notes.feature.noteeditor

import com.androidatlas.notes.core.database.entity.SyncOperationEntity
import com.androidatlas.notes.core.database.repository.NoteRepository
import com.androidatlas.notes.core.sync.processor.SyncQueueProcessor
import android.util.Log
import java.time.Instant
import java.util.UUID

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository,
    private val syncQueueProcessor: SyncQueueProcessor
) {
    companion object {
        private const val TAG = "DeleteNoteUseCase"
    }

    suspend fun execute(noteId: String) {
        val now = Instant.now().toString()
        Log.d(TAG, "execute() — DELETE noteId=$noteId")

        noteRepository.deleteNote(noteId)
        Log.d(TAG, "execute() — soft-deleted in local DB")

        val syncOperation = SyncOperationEntity(
            localId = UUID.randomUUID().toString(),
            operationType = "DELETE",
            noteId = noteId,
            title = null,
            content = null,
            folderOrLabel = null,
            clientUpdatedAt = now,
            status = "PENDING"
        )
        syncQueueProcessor.enqueue(syncOperation)
        Log.d(TAG, "execute() — enqueued sync operation")
    }
}
