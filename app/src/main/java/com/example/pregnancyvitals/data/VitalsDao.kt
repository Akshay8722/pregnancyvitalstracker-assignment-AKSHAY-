package com.example.pregnancyvitals.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vital: Vital)

    @Query("SELECT * FROM vital_table ORDER BY timestamp DESC")
    fun getAllVitals(): Flow<List<Vital>>
}
