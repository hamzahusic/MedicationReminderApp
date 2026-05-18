package com.example.medicationreminderapp.data.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.medicationreminderapp.data.model.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Query("SELECT * FROM reminders WHERE medication_id = :medicationId LIMIT 1")
    fun observeReminderByMedication(medicationId: Int): Flow<ReminderEntity?>

    @Query("SELECT * FROM reminders WHERE medication_id = :medicationId LIMIT 1")
    suspend fun getReminderByMedication(medicationId: Int): ReminderEntity?

    @Query("SELECT * FROM reminders WHERE id = :id LIMIT 1")
    suspend fun getReminderById(id: Int): ReminderEntity?

    @Update
    suspend fun updateReminder(reminder: ReminderEntity)

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)
}
