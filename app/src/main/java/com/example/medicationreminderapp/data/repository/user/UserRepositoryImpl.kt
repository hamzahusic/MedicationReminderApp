package com.example.medicationreminderapp.data.repository.user

import com.example.medicationreminderapp.data.model.local.dao.UserDao
import com.example.medicationreminderapp.data.model.local.entity.UserEntity
import com.example.medicationreminderapp.data.repository.user.mapper.toUserEntity
import com.example.medicationreminderapp.presentation.view_model.auth.util.RegisterUserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(userData: RegisterUserData) {
        userDao.insertUser(userData.toUserEntity())
    }

    override suspend fun getUserByEmailAndPassword(
        email: String,
        password: String
    ): UserEntity? {
        return userDao.getUserByEmailAndPassword(
            email = email,
            password = password
        )
    }

    override suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    override suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }

    override fun observeUserById(id: Int): Flow<UserEntity?> {
        return userDao.observeUserById(id)
    }

    override suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }
}