package com.androidatlas.notes.core.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androidatlas.notes.core.database.converter.SyncStatusConverter
import com.androidatlas.notes.core.database.dao.NoteDao
import com.androidatlas.notes.core.database.dao.SyncOperationDao
import com.androidatlas.notes.core.database.entity.NoteEntity
import com.androidatlas.notes.core.database.entity.NoteFts
import com.androidatlas.notes.core.database.entity.SyncOperationEntity

@Database(
    entities = [NoteEntity::class, SyncOperationEntity::class, NoteFts::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(SyncStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun syncOperationDao(): SyncOperationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
