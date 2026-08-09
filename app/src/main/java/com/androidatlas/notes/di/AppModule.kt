package com.androidatlas.notes.di

import android.content.Context
import com.androidatlas.notes.core.database.db.AppDatabase
import com.androidatlas.notes.core.database.mapper.NoteEntityMapper
import com.androidatlas.notes.core.database.repository.NoteRepository
import com.androidatlas.notes.core.network.retrofit.RetrofitClient
import com.androidatlas.notes.core.sync.backoff.BackoffPolicy
import com.androidatlas.notes.core.sync.processor.SyncQueueProcessor
import com.androidatlas.notes.feature.noteeditor.DeleteNoteUseCase
import com.androidatlas.notes.feature.noteeditor.SaveNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Database
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideNoteEntityMapper(): NoteEntityMapper {
        return NoteEntityMapper()
    }

    @Singleton
    @Provides
    fun provideNoteRepository(
        database: AppDatabase,
        mapper: NoteEntityMapper
    ): NoteRepository {
        return NoteRepository(
            noteDao = database.noteDao(),
            noteEntityMapper = mapper
        )
    }

    // Sync
    @Singleton
    @Provides
    fun provideBackoffPolicy(): BackoffPolicy {
        return BackoffPolicy()
    }

    @Singleton
    @Provides
    fun provideSyncQueueProcessor(
        @ApplicationContext context: Context,
        database: AppDatabase,
        notesApiService: com.androidatlas.notes.core.network.api.NotesApiService,
        backoffPolicy: BackoffPolicy
    ): SyncQueueProcessor {
        return SyncQueueProcessor(
            syncOperationDao = database.syncOperationDao(),
            noteDao = database.noteDao(),
            notesApiService = notesApiService,
            backoffPolicy = backoffPolicy,
            appContext = context
        )
    }

    // Network
    @Singleton
    @Provides
    fun provideAuthApiService(): com.androidatlas.notes.core.network.api.AuthApiService {
        return RetrofitClient.authApiService
    }

    @Singleton
    @Provides
    fun provideNotesApiService(): com.androidatlas.notes.core.network.api.NotesApiService {
        return RetrofitClient.notesApiService
    }

    // Use Cases
    @Singleton
    @Provides
    fun provideSaveNoteUseCase(
        noteRepository: NoteRepository,
        syncQueueProcessor: SyncQueueProcessor
    ): SaveNoteUseCase {
        return SaveNoteUseCase(
            noteRepository = noteRepository,
            syncQueueProcessor = syncQueueProcessor
        )
    }

    @Singleton
    @Provides
    fun provideDeleteNoteUseCase(
        noteRepository: NoteRepository,
        syncQueueProcessor: SyncQueueProcessor
    ): DeleteNoteUseCase {
        return DeleteNoteUseCase(
            noteRepository = noteRepository,
            syncQueueProcessor = syncQueueProcessor
        )
    }
}
