package com.example.medicationreminderapp.data.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.medicationreminderapp.data.model.local.entity.DosageHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DosageHistoryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDosageHistory(dosageHistory: DosageHistoryEntity): Long

    @Query("SELECT * FROM dosage_history WHERE schedule_id = :scheduleId ORDER BY scheduled_date DESC")
    fun observeHistoryBySchedule(scheduleId: Int): Flow<List<DosageHistoryEntity>>

    @Query("SELECT * FROM dosage_history WHERE scheduled_date BETWEEN :startOfDay AND :endOfDay ORDER BY scheduled_date ASC")
    fun observeHistoryForDay(startOfDay: Long, endOfDay: Long): Flow<List<DosageHistoryEntity>>

    @Query("SELECT * FROM dosage_history WHERE scheduled_date BETWEEN :startOfDay AND :endOfDay ORDER BY scheduled_date ASC")
    suspend fun getHistoryForDay(startOfDay: Long, endOfDay: Long): List<DosageHistoryEntity>

    @Update
    suspend fun updateDosageHistory(dosageHistory: DosageHistoryEntity)

    @Delete
    suspend fun deleteDosageHistory(dosageHistory: DosageHistoryEntity)
}
