package com.example.medicationreminderapp.data.service

import com.example.medicationreminderapp.data.model.remote.MedicationFirestoreDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreService @Inject constructor(
    private val db: FirebaseFirestore
) {

    private fun medicationsRef(uid: String) =
        db.collection("users").document(uid).collection("medications")

    suspend fun saveMedication(uid: String, dto: MedicationFirestoreDto) {
        medicationsRef(uid).document(dto.id).set(dto).await()
    }

    suspend fun deleteMedication(uid: String, medicationId: String) {
        medicationsRef(uid).document(medicationId).delete().await()
    }

    fun observeMedications(uid: String): Flow<List<MedicationFirestoreDto>> =
        medicationsRef(uid).snapshots().map { snapshot ->
            snapshot.documents.mapNotNull {
                it.toObject(MedicationFirestoreDto::class.java)
            }
        }
}
