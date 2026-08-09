package com.androidatlas.notes.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.androidatlas.notes.core.database.entity.SyncOperationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncOperationDao {
    @Query("SELECT * FROM sync_operations WHERE status = 'PENDING' ORDER BY id ASC")
    suspend fun getPendingOperations(): List<SyncOperationEntity>

    @Query("SELECT COUNT(*) FROM sync_operations WHERE status IN ('PENDING', 'SYNCING')")
    fun observePendingCount(): Flow<Int>

    @Insert
    suspend fun enqueueOperation(operation: SyncOperationEntity)

    @Query("DELETE FROM sync_operations WHERE id = :id")
    suspend fun deleteOperation(id: Long)

    @Query("UPDATE sync_operations SET status = :status WHERE id = :id")
    suspend fun updateOperationStatus(id: Long, status: String)

    @Query("DELETE FROM sync_operations WHERE noteId = :noteId AND status = 'PENDING'")
    suspend fun deletePendingOperationsForNote(noteId: String)
}