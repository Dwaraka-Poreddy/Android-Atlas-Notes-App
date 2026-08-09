package com.androidatlas.notes

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.androidatlas.notes.core.sync.worker.SyncScheduler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidAtlasApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Schedule background sync work on app startup
        SyncScheduler.scheduleSyncWork(this)
    }
}
