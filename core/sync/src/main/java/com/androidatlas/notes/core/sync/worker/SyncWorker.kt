package com.androidatlas.notes.core.sync.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.androidatlas.notes.core.database.db.AppDatabase
import com.androidatlas.notes.core.network.retrofit.RetrofitClient
import com.androidatlas.notes.core.sync.backoff.BackoffPolicy
import com.androidatlas.notes.core.sync.processor.SyncQueueProcessor

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "SyncWorker"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork() START — attempt #$runAttemptCount")
        return try {
            val database = AppDatabase.getInstance(applicationContext)
            val syncProcessor = SyncQueueProcessor(
                syncOperationDao = database.syncOperationDao(),
                noteDao = database.noteDao(),
                notesApiService = RetrofitClient.notesApiService,
                backoffPolicy = BackoffPolicy(),
                appContext = applicationContext
            )

            syncProcessor.processPendingOperations()
            Log.d(TAG, "doWork() SUCCESS")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "doWork() FAILED — scheduling retry", e)
            Result.retry()
        }
    }
}
