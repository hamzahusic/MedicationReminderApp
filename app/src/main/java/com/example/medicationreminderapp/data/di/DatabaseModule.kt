package com.example.medicationreminderapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.medicationreminderapp.data.model.local.dao.DosageHistoryDao
import com.example.medicationreminderapp.data.model.local.dao.MedicationDao
import com.example.medicationreminderapp.data.model.local.dao.MedicationScheduleDao
import com.example.medicationreminderapp.data.model.local.dao.ReminderDao
import com.example.medicationreminderapp.data.model.local.dao.UserDao
import com.example.medicationreminderapp.data.model.local.db.AppDatabase
import com.example.medicationreminderapp.data.repository.dosage_history.DosageHistoryRepository
import com.example.medicationreminderapp.data.repository.dosage_history.DosageHistoryRepositoryImpl
import com.example.medicationreminderapp.data.repository.medication.MedicationRepository
import com.example.medicationreminderapp.data.repository.medication.MedicationRepositoryImpl
import com.example.medicationreminderapp.data.repository.user.SessionRepository
import com.example.medicationreminderapp.data.repository.user.SessionRepositoryImpl
import com.example.medicationreminderapp.data.repository.user.UserRepository
import com.example.medicationreminderapp.data.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "liferPg_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    // --- DAOs ---

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideMedicationDao(database: AppDatabase): MedicationDao {
        return database.medicationDao()
    }

    @Provides
    @Singleton
    fun provideMedicationScheduleDao(database: AppDatabase): MedicationScheduleDao {
        return database.medicationScheduleDao()
    }

    @Provides
    @Singleton
    fun provideDosageHistoryDao(database: AppDatabase): DosageHistoryDao {
        return database.dosageHistoryDao()
    }

    @Provides
    @Singleton
    fun provideReminderDao(database: AppDatabase): ReminderDao {
        return database.reminderDao()
    }

    // --- Repositories ---

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(
        @ApplicationContext context: Context
    ): SessionRepository {
        return SessionRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideMedicationRepository(
        medicationDao: MedicationDao,
        medicationScheduleDao: MedicationScheduleDao
    ): MedicationRepository {
        return MedicationRepositoryImpl(medicationDao, medicationScheduleDao)
    }

    @Provides
    @Singleton
    fun provideDosageHistoryRepository(
        dosageHistoryDao: DosageHistoryDao
    ): DosageHistoryRepository {
        return DosageHistoryRepositoryImpl(dosageHistoryDao)
    }
}
