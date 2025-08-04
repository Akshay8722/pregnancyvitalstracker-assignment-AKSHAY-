package com.example.pregnancyvitals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.*
import com.example.pregnancyvitals.data.VitalDatabase
import com.example.pregnancyvitals.repository.VitalRepository
import com.example.pregnancyvitals.viewmodel.VitalViewModel
import com.example.pregnancyvitals.viewmodel.VitalViewModelFactory
import com.example.pregnancyvitals.ui.theme.PregnancyVitalsTheme
import com.example.pregnancyvitals.worker.ReminderWorker
import com.example.pregnancyvitals.VitalsScreen
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = VitalDatabase.getDatabase(this).vitalDao()
        val repo = VitalRepository(dao)
        val factory = VitalViewModelFactory(repo)

        setContent {
            PregnancyVitalsTheme {
                val viewModel: VitalViewModel = viewModel(factory = factory)
                VitalsScreen(viewModel)
            }
        }

        val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(5, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "VitalsReminder",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
