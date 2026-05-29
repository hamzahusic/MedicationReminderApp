package com.example.medicationreminderapp.data.model.remote

import com.google.gson.annotations.SerializedName

data class CreateMedicationRequest(
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val name: String,
    @SerializedName("body") val details: String
)
