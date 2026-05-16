package com.example.medicationreminderapp.data.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "dosage_history",
    foreignKeys = [
        ForeignKey(
            entity = MedicationScheduleEntity::class,
            parentColumns = ["id"],
            childColumns = ["schedule_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["schedule_id"])]
)
data class DosageHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "schedule_id")
    val scheduleId: Int,

    @ColumnInfo(name = "scheduled_date")
    val scheduledDate: Long,

    @ColumnInfo(name = "is_taken")
    val isTaken: Boolean = false,

    @ColumnInfo(name = "taken_at")
    val takenAt: Long? = null
)
