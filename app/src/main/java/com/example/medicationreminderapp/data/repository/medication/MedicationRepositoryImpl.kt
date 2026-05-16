package com.example.medicationreminderapp.data.repository.medication

import com.example.medicationreminderapp.data.model.local.dao.MedicationDao
import com.example.medicationreminderapp.data.model.local.dao.MedicationScheduleDao
import com.example.medicationreminderapp.data.repository.medication.mapper.toDomain
import com.example.medicationreminderapp.data.repository.medication.mapper.toMedicationEntity
import com.example.medicationreminderapp.data.repository.medication.mapper.toScheduleEntity
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import com.example.medicationreminderapp.presentation.view_model.add_medication.AddMedicationData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MedicationRepositoryImpl @Inject constructor(
    private val medicationDao: MedicationDao,
    private val medicationScheduleDao: MedicationScheduleDao
) : MedicationRepository {

    override suspend fun insertMedication(data: AddMedicationData) {
        val medicationId = medicationDao.insertMedication(data.toMedicationEntity())
        medicationScheduleDao.insertSchedule(data.toScheduleEntity(medicationId.toInt()))
    }

    override fun observeMedicationsByUser(userId: Int): Flow<List<Medication>> {
        return medicationDao.observeMedicationsWithSchedulesByUser(userId).map { list ->
            list.mapNotNull { it.toDomain() }
        }
    }

    override suspend fun getMedicationsByUser(userId: Int): List<Medication> {
        return medicationDao.getMedicationsWithSchedulesByUser(userId).mapNotNull { it.toDomain() }
    }

    override suspend fun getMedicationById(id: Int): Medication? {
        return medicationDao.getMedicationWithScheduleById(id)?.toDomain()
    }

    override suspend fun updateMedication(medication: Medication, userId: Int) {
        medicationDao.updateMedication(medication.toMedicationEntity(userId))
    }

    override suspend fun deleteMedication(medication: Medication, userId: Int) {
        medicationDao.deleteMedication(medication.toMedicationEntity(userId))
    }
}
