package com.androidatlas.notes.core.sync.worker

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object SyncScheduler {
    private const val TAG = "SyncScheduler"

    fun scheduleSyncWork(context: Context) {
        Log.d(TAG, "scheduleSyncWork() called — enqueuing OneTimeWorkRequest")

        val syncWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                1,
                TimeUnit.SECONDS
            )
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "sync_notes",
            androidx.work.ExistingWorkPolicy.KEEP,
            syncWorkRequest
        )
        Log.d(TAG, "WorkManager enqueueUniqueWork completed (policy=KEEP, name=sync_notes)")
    }
}
