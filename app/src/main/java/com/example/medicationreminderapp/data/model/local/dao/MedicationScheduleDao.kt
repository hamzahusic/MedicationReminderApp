package com.example.medicationreminderapp.data.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.medicationreminderapp.data.model.local.entity.MedicationScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationScheduleDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSchedule(schedule: MedicationScheduleEntity): Long

    @Query("SELECT * FROM medication_schedules WHERE medication_id = :medicationId ORDER BY hour ASC, minute ASC")
    fun observeSchedulesByMedication(medicationId: Int): Flow<List<MedicationScheduleEntity>>

    @Query("SELECT * FROM medication_schedules WHERE medication_id = :medicationId ORDER BY hour ASC, minute ASC")
    suspend fun getSchedulesByMedication(medicationId: Int): List<MedicationScheduleEntity>

    @Query("SELECT * FROM medication_schedules WHERE id = :id LIMIT 1")
    suspend fun getScheduleById(id: Int): MedicationScheduleEntity?

    @Update
    suspend fun updateSchedule(schedule: MedicationScheduleEntity)

    @Delete
    suspend fun deleteSchedule(schedule: MedicationScheduleEntity)
}
