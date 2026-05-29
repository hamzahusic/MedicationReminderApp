package com.example.medicationreminderapp.data.service

import com.example.medicationreminderapp.data.model.remote.CreateMedicationRequest
import com.example.medicationreminderapp.data.model.remote.MedicationDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MedicationApiService {

    @GET("posts")
    suspend fun getMedications(): List<MedicationDto>

    @GET("posts/{id}")
    suspend fun getMedicationById(@Path("id") id: Int): MedicationDto

    @POST("posts")
    suspend fun createMedication(@Body request: CreateMedicationRequest): MedicationDto

    @PUT("posts/{id}")
    suspend fun updateMedication(
        @Path("id") id: Int,
        @Body request: CreateMedicationRequest
    ): MedicationDto

    @DELETE("posts/{id}")
    suspend fun deleteMedication(@Path("id") id: Int): Response<Unit>
}
