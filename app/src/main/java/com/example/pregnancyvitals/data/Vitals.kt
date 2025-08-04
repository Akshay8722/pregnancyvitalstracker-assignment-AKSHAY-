package com.example.pregnancyvitals.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vital_table")
data class Vital(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val systolicBP: Int,
    val diastolicBP: Int,
    val heartRate: Int,
    val weight: Float,
    val babyKicks: Int,
    val timestamp: Long = System.currentTimeMillis()
)
