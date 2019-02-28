package com.sample.todo.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface ListenableWorkerFactory {
    fun create(appContext: Context, workerParams: WorkerParameters): ListenableWorker
}