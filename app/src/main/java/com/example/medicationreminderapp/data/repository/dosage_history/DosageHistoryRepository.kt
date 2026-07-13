package com.example.medicationreminderapp.data.repository.dosage_history

import com.example.medicationreminderapp.data.model.local.entity.DosageHistoryEntity
import kotlinx.coroutines.flow.Flow

interface DosageHistoryRepository {
    suspend fun insertDosageHistory(dosageHistory: DosageHistoryEntity)
    fun observeHistoryForDay(startOfDay: Long, endOfDay: Long): Flow<List<DosageHistoryEntity>>
    fun observeTakenScheduleIdsForDay(startOfDay: Long, endOfDay: Long): Flow<List<Int>>
    suspend fun getHistoryForDay(startOfDay: Long, endOfDay: Long): List<DosageHistoryEntity>
    suspend fun takeMedication(scheduleId: Int, scheduledDate: Long)
    suspend fun updateDosageHistory(dosageHistory: DosageHistoryEntity)
    suspend fun deleteDosageHistory(dosageHistory: DosageHistoryEntity)
}
