package com.example.medicationreminderapp.data.model.local.entity

import androidx.room.ColumnInfo

data class MedicationWithScheduleEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "dosage") val dosage: String,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "schedule_id") val scheduleId: Int?,
    @ColumnInfo(name = "hour") val hour: Int?,
    @ColumnInfo(name = "minute") val minute: Int?
)
