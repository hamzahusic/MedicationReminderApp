package com.example.medicationreminderapp.data.repository.user

import com.example.medicationreminderapp.data.model.local.entity.UserEntity
import com.example.medicationreminderapp.presentation.view_model.auth.util.RegisterUserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(userData: RegisterUserData)
    suspend fun getUserByEmailAndPassword(email: String, password: String): UserEntity?
    suspend fun getUserByEmail(email: String): UserEntity?
    suspend fun getUserById(id: Int): UserEntity?
    fun observeUserById(id: Int): Flow<UserEntity?>
    suspend fun updateUser(user: UserEntity)
    suspend fun deleteUser(user: UserEntity)
}