package com.example.medicationreminderapp.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val auth: FirebaseAuth
) {

    val currentUser: FirebaseUser? get() = auth.currentUser

    suspend fun register(email: String, password: String): String {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        return result.user?.uid
            ?: throw IllegalStateException("Firebase UID is null after registration.")
    }

    suspend fun login(email: String, password: String): String {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user?.uid
            ?: throw IllegalStateException("Firebase UID is null after login.")
    }

    fun logout() = auth.signOut()
}
