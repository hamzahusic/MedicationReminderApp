package com.example.medicationreminderapp.data.repository.dosage_history

import com.example.medicationreminderapp.data.model.local.dao.DosageHistoryDao
import com.example.medicationreminderapp.data.model.local.entity.DosageHistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DosageHistoryRepositoryImpl @Inject constructor(
    private val dosageHistoryDao: DosageHistoryDao
) : DosageHistoryRepository {

    override suspend fun insertDosageHistory(dosageHistory: DosageHistoryEntity) {
        dosageHistoryDao.insertDosageHistory(dosageHistory)
    }

    override fun observeHistoryForDay(startOfDay: Long, endOfDay: Long): Flow<List<DosageHistoryEntity>> {
        return dosageHistoryDao.observeHistoryForDay(startOfDay, endOfDay)
    }

    override fun observeTakenScheduleIdsForDay(startOfDay: Long, endOfDay: Long): Flow<List<Int>> {
        return dosageHistoryDao.observeTakenScheduleIdsForDay(startOfDay, endOfDay)
    }

    override suspend fun getHistoryForDay(startOfDay: Long, endOfDay: Long): List<DosageHistoryEntity> {
        return dosageHistoryDao.getHistoryForDay(startOfDay, endOfDay)
    }

    override suspend fun takeMedication(scheduleId: Int, scheduledDate: Long) {
        dosageHistoryDao.insertDosageHistory(
            DosageHistoryEntity(
                scheduleId = scheduleId,
                scheduledDate = scheduledDate,
                isTaken = true,
                takenAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun updateDosageHistory(dosageHistory: DosageHistoryEntity) {
        dosageHistoryDao.updateDosageHistory(dosageHistory)
    }

    override suspend fun deleteDosageHistory(dosageHistory: DosageHistoryEntity) {
        dosageHistoryDao.deleteDosageHistory(dosageHistory)
    }
}
