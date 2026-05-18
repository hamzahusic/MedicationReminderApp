package com.example.medicationreminderapp.data.repository.medication

import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import com.example.medicationreminderapp.presentation.view_model.add_medication.AddMedicationData
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {
    suspend fun insertMedication(data: AddMedicationData)
    fun observeMedicationsByUser(userId: Int): Flow<List<Medication>>
    suspend fun getMedicationsByUser(userId: Int): List<Medication>
    suspend fun getMedicationById(id: Int): Medication?
    suspend fun updateMedication(medication: Medication, userId: Int)
    suspend fun deleteMedication(medication: Medication, userId: Int)
}
