package com.example.medicationreminderapp.data.di

import com.example.medicationreminderapp.data.service.FirebaseAuthService
import com.example.medicationreminderapp.data.service.FirestoreService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthService(auth: FirebaseAuth): FirebaseAuthService =
        FirebaseAuthService(auth)

    @Provides
    @Singleton
    fun provideFirestoreService(db: FirebaseFirestore): FirestoreService =
        FirestoreService(db)
}
