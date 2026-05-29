package com.example.medicationreminderapp.data.repository.medication

import com.example.medicationreminderapp.data.model.remote.CreateMedicationRequest
import com.example.medicationreminderapp.data.model.remote.MedicationDto

interface MedicationNetworkRepository {
    suspend fun getRemoteMedications(): List<MedicationDto>
    suspend fun getRemoteMedicationById(id: Int): MedicationDto
    suspend fun createRemoteMedication(request: CreateMedicationRequest): MedicationDto
    suspend fun updateRemoteMedication(id: Int, request: CreateMedicationRequest): MedicationDto
    suspend fun deleteRemoteMedication(id: Int)
}
