package com.example.pregnancyvitals.repository

import com.example.pregnancyvitals.data.Vital
import com.example.pregnancyvitals.data.VitalDao
import kotlinx.coroutines.flow.Flow

class VitalRepository(private val dao: VitalDao) {
    val allVitals: Flow<List<Vital>> = dao.getAllVitals()
    suspend fun insert(vital: Vital) = dao.insert(vital)
}
