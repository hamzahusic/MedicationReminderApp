package com.example.medicationreminderapp.data.repository.user.mapper

import com.example.medicationreminderapp.data.model.local.entity.UserEntity
import com.example.medicationreminderapp.presentation.view_model.auth.util.RegisterUserData

fun RegisterUserData.toUserEntity(): UserEntity {
    return UserEntity(
        username = username,
        email = email,
        passwordHash = password
    )
}
