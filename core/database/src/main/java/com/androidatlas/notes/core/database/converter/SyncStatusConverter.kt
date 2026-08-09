package com.androidatlas.notes.core.database.converter

import androidx.room.TypeConverter
import com.androidatlas.notes.core.database.entity.SyncStatus

class SyncStatusConverter {
    @TypeConverter
    fun fromSyncStatus(value: SyncStatus): String {
        return value.name
    }

    @TypeConverter
    fun toSyncStatus(value: String): SyncStatus {
        return SyncStatus.valueOf(value)
    }
}
