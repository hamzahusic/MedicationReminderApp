package com.example.medicationreminderapp.data.repository.medication.mapper

import com.example.medicationreminderapp.data.model.local.entity.MedicationEntity
import com.example.medicationreminderapp.data.model.local.entity.MedicationScheduleEntity
import com.example.medicationreminderapp.data.model.local.entity.MedicationWithScheduleEntity
import com.example.medicationreminderapp.presentation.ui.screens.home.util.Medication
import com.example.medicationreminderapp.presentation.view_model.add_medication.AddMedicationData

fun MedicationWithScheduleEntity.toDomain(): Medication? {
    val h = hour ?: return null
    val m = minute ?: return null
    return Medication(
        id = id,
        name = name,
        dosage = dosage,
        takeAtHour = h,
        takeAtMinute = m,
        scheduleId = scheduleId ?: 0
    )
}

fun AddMedicationData.toMedicationEntity(): MedicationEntity {
    return MedicationEntity(
        name = name,
        dosage = dosage,
        userId = userId
    )
}

fun AddMedicationData.toScheduleEntity(medicationId: Int): MedicationScheduleEntity {
    return MedicationScheduleEntity(
        medicationId = medicationId,
        hour = hour,
        minute = minute
    )
}

fun Medication.toMedicationEntity(userId: Int): MedicationEntity {
    return MedicationEntity(
        id = id,
        name = name,
        dosage = dosage,
        userId = userId
    )
}
