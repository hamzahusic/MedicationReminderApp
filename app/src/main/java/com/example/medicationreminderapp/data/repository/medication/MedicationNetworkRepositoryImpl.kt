package com.example.medicationreminderapp.data.repository.medication

import com.example.medicationreminderapp.data.model.remote.CreateMedicationRequest
import com.example.medicationreminderapp.data.model.remote.MedicationDto
import com.example.medicationreminderapp.data.service.MedicationApiService
import javax.inject.Inject

class MedicationNetworkRepositoryImpl @Inject constructor(
    private val apiService: MedicationApiService
) : MedicationNetworkRepository {

    override suspend fun getRemoteMedications(): List<MedicationDto> =
        apiService.getMedications()

    override suspend fun getRemoteMedicationById(id: Int): MedicationDto =
        apiService.getMedicationById(id)

    override suspend fun createRemoteMedication(request: CreateMedicationRequest): MedicationDto =
        apiService.createMedication(request)

    override suspend fun updateRemoteMedication(
        id: Int,
        request: CreateMedicationRequest
    ): MedicationDto = apiService.updateMedication(id, request)

    override suspend fun deleteRemoteMedication(id: Int) {
        apiService.deleteMedication(id)
    }
}
