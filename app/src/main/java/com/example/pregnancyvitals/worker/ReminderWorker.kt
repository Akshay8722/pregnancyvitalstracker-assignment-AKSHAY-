package com.example.pregnancyvitals.worker

import android.content.Context
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Toast.makeText(applicationContext, "Reminder: Add your pregnancy vitals", Toast.LENGTH_LONG).show()
        return Result.success()
    }
}
