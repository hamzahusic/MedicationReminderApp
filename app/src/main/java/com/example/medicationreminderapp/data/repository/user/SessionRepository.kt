package com.example.medicationreminderapp.data.repository.user

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun saveLoggedInUserId(userId: Int)
    fun observeLoggedInUserId(): Flow<Int?>
    suspend fun clearSession()
}