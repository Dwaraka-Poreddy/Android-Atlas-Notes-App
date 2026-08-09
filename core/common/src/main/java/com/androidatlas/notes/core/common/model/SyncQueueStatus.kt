package com.androidatlas.notes.core.common.model

data class SyncQueueStatus(
    val pendingCount: Int, // number of operations waiting to sync
    val isSyncing: Boolean, // currently syncing?
    val lastSyncedAt: String? // ISO 8601 timestamp of last successful sync, null if never synced
)