package com.androidatlas.notes.core.sync.processor

import com.androidatlas.notes.core.database.dao.NoteDao
import com.androidatlas.notes.core.database.dao.SyncOperationDao
import com.androidatlas.notes.core.database.entity.NoteEntity
import com.androidatlas.notes.core.database.entity.SyncOperationEntity
import com.androidatlas.notes.core.database.entity.SyncStatus
import com.androidatlas.notes.core.common.model.SyncQueueStatus
import com.androidatlas.notes.core.network.api.NotesApiService
import com.androidatlas.notes.core.network.dto.SyncOperationDto
import com.androidatlas.notes.core.sync.worker.SyncScheduler
import com.androidatlas.notes.core.network.dto.SyncRequestDto
import com.androidatlas.notes.core.sync.backoff.BackoffPolicy
import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SyncQueueProcessor(
    private val syncOperationDao: SyncOperationDao,
    private val noteDao: NoteDao,
    private val notesApiService: NotesApiService,
    private val backoffPolicy: BackoffPolicy,
    private val appContext: Context
) {
    private var isSyncing = false
    private var lastSyncedAt: String? = null

    companion object {
        private const val TAG = "SyncQueueProcessor"
    }

    suspend fun enqueue(operation: SyncOperationEntity) {
        Log.d(TAG, "enqueue() — type=${operation.operationType}, noteId=${operation.noteId}")

        val existingPendingOps = syncOperationDao.getPendingOperations()
        val duplicateOp = existingPendingOps.find {
            it.noteId == operation.noteId && it.operationType == operation.operationType
        }

        if (duplicateOp != null) {
            Log.d(TAG, "enqueue() — collapsing duplicate op id=${duplicateOp.id}")
            syncOperationDao.deleteOperation(duplicateOp.id)
        }

        syncOperationDao.enqueueOperation(operation)
        Log.d(TAG, "enqueue() — operation saved to DB, scheduling SyncWorker")
        SyncScheduler.scheduleSyncWork(appContext)
    }

    suspend fun processPendingOperations() {
        Log.d(TAG, "processPendingOperations() START — isSyncing=$isSyncing")
        if (isSyncing) {
            Log.w(TAG, "processPendingOperations() SKIPPED — already syncing")
            return
        }

        isSyncing = true
        try {
            val pendingOps = syncOperationDao.getPendingOperations()
            Log.d(TAG, "processPendingOperations() — ${pendingOps.size} pending ops")
            if (pendingOps.isEmpty()) {
                Log.d(TAG, "processPendingOperations() — nothing to sync")
                return
            }

            pendingOps.forEachIndexed { i, op ->
                Log.d(TAG, "  op[$i]: id=${op.id}, type=${op.operationType}, noteId=${op.noteId}, status=${op.status}")
            }

            val operationDtos = pendingOps.map { op ->
                SyncOperationDto(
                    localId = op.localId,
                    operationType = op.operationType,
                    noteId = op.noteId,
                    title = op.title,
                    content = op.content,
                    folderOrLabel = op.folderOrLabel,
                    clientUpdatedAt = op.clientUpdatedAt
                )
            }

            val syncRequest = SyncRequestDto(operationDtos)
            Log.d(TAG, "processPendingOperations() — sending ${operationDtos.size} ops to server")
            val syncResponse = notesApiService.sync(syncRequest)
            Log.d(TAG, "processPendingOperations() — server returned ${syncResponse.results.size} results")

            syncResponse.results.forEach { result ->
                Log.d(TAG, "  result: localId=${result.localId}, status=${result.status}")
                val localOp = pendingOps.find { it.localId == result.localId }
                if (localOp == null) {
                    Log.w(TAG, "  result localId=${result.localId} has no matching local op — skipping")
                }
                if (localOp != null) {
                    when (result.status) {
                        "applied" -> {
                            Log.d(TAG, "  applied — updating local note and removing op")
                            result.note?.let { noteDto ->
                                val noteEntity = NoteEntity(
                                    id = noteDto.id,
                                    title = noteDto.title,
                                    content = noteDto.content,
                                    folderOrLabel = noteDto.folderOrLabel,
                                    updatedAt = noteDto.updatedAt,
                                    deleted = noteDto.deleted,
                                    syncStatus = SyncStatus.SYNCED
                                )
                                noteDao.insertOrUpdateNote(noteEntity)
                            }
                            syncOperationDao.deleteOperation(localOp.id)
                        }
                        "conflict" -> {
                            Log.w(TAG, "  conflict — server wins, overwriting local note")
                            result.note?.let { noteDto ->
                                val noteEntity = NoteEntity(
                                    id = noteDto.id,
                                    title = noteDto.title,
                                    content = noteDto.content,
                                    folderOrLabel = noteDto.folderOrLabel,
                                    updatedAt = noteDto.updatedAt,
                                    deleted = noteDto.deleted,
                                    syncStatus = SyncStatus.SYNCED
                                )
                                noteDao.insertOrUpdateNote(noteEntity)
                            }
                            syncOperationDao.deleteOperation(localOp.id)
                        }
                        "error" -> {
                            val attemptNumber = localOp.id.toInt() % 10
                            Log.e(TAG, "  error — attemptNumber=$attemptNumber, shouldRetry=${backoffPolicy.shouldRetry(attemptNumber)}")
                            if (backoffPolicy.shouldRetry(attemptNumber)) {
                                syncOperationDao.updateOperationStatus(localOp.id, "PENDING")
                            } else {
                                Log.e(TAG, "  error — max retries reached, marking FAILED")
                                syncOperationDao.updateOperationStatus(localOp.id, "FAILED")
                            }
                        }
                        else -> {
                            Log.w(TAG, "  unknown status '${result.status}' — ignoring")
                        }
                    }
                }
            }

            lastSyncedAt = System.currentTimeMillis().toString()
            Log.d(TAG, "processPendingOperations() COMPLETE — lastSyncedAt=$lastSyncedAt")
        } catch (e: Exception) {
            Log.e(TAG, "processPendingOperations() EXCEPTION — ${e.javaClass.simpleName}: ${e.message}", e)
            val pendingOps = syncOperationDao.getPendingOperations()
            pendingOps.forEach { op ->
                syncOperationDao.updateOperationStatus(op.id, "PENDING")
            }
        } finally {
            isSyncing = false
            Log.d(TAG, "processPendingOperations() FINALLY — isSyncing reset to false")
        }
    }

    fun observeQueueStatus(): Flow<SyncQueueStatus> {
        return syncOperationDao.observePendingCount().map { count ->
            SyncQueueStatus(
                pendingCount = count,
                isSyncing = isSyncing,
                lastSyncedAt = lastSyncedAt
            )
        }
    }
}
