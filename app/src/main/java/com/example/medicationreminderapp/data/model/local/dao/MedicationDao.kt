package com.example.medicationreminderapp.data.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.medicationreminderapp.data.model.local.entity.MedicationEntity
import com.example.medicationreminderapp.data.model.local.entity.MedicationWithScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMedication(medication: MedicationEntity): Long

    @Query("""
        SELECT m.id, m.name, m.dosage, m.user_id, m.created_at,
               ms.id AS schedule_id, ms.hour, ms.minute
        FROM medications m
        LEFT JOIN medication_schedules ms ON ms.medication_id = m.id
        WHERE m.user_id = :userId
        ORDER BY m.name ASC
    """)
    fun observeMedicationsWithSchedulesByUser(userId: Int): Flow<List<MedicationWithScheduleEntity>>

    @Query("""
        SELECT m.id, m.name, m.dosage, m.user_id, m.created_at,
               ms.id AS schedule_id, ms.hour, ms.minute
        FROM medications m
        LEFT JOIN medication_schedules ms ON ms.medication_id = m.id
        WHERE m.user_id = :userId
        ORDER BY m.name ASC
    """)
    suspend fun getMedicationsWithSchedulesByUser(userId: Int): List<MedicationWithScheduleEntity>

    @Query("""
        SELECT m.id, m.name, m.dosage, m.user_id, m.created_at,
               ms.id AS schedule_id, ms.hour, ms.minute
        FROM medications m
        LEFT JOIN medication_schedules ms ON ms.medication_id = m.id
        WHERE m.id = :id
        LIMIT 1
    """)
    suspend fun getMedicationWithScheduleById(id: Int): MedicationWithScheduleEntity?

    @Query("SELECT * FROM medications WHERE id = :id LIMIT 1")
    suspend fun getMedicationById(id: Int): MedicationEntity?

    @Update
    suspend fun updateMedication(medication: MedicationEntity)

    @Delete
    suspend fun deleteMedication(medication: MedicationEntity)
}
