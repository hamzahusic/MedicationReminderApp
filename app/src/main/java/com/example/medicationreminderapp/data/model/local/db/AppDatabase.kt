package com.example.medicationreminderapp.data.model.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.medicationreminderapp.data.model.local.dao.DosageHistoryDao
import com.example.medicationreminderapp.data.model.local.dao.MedicationDao
import com.example.medicationreminderapp.data.model.local.dao.MedicationScheduleDao
import com.example.medicationreminderapp.data.model.local.dao.ReminderDao
import com.example.medicationreminderapp.data.model.local.dao.UserDao
import com.example.medicationreminderapp.data.model.local.entity.DosageHistoryEntity
import com.example.medicationreminderapp.data.model.local.entity.MedicationEntity
import com.example.medicationreminderapp.data.model.local.entity.MedicationScheduleEntity
import com.example.medicationreminderapp.data.model.local.entity.ReminderEntity
import com.example.medicationreminderapp.data.model.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        MedicationEntity::class,
        MedicationScheduleEntity::class,
        DosageHistoryEntity::class,
        ReminderEntity::class,
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun medicationDao(): MedicationDao
    abstract fun medicationScheduleDao(): MedicationScheduleDao
    abstract fun dosageHistoryDao(): DosageHistoryDao
    abstract fun reminderDao(): ReminderDao
}
