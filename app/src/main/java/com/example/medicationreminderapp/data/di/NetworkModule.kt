package com.example.medicationreminderapp.data.di

import com.example.medicationreminderapp.data.repository.medication.MedicationNetworkRepository
import com.example.medicationreminderapp.data.repository.medication.MedicationNetworkRepositoryImpl
import com.example.medicationreminderapp.data.service.MedicationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMedicationApiService(retrofit: Retrofit): MedicationApiService =
        retrofit.create(MedicationApiService::class.java)

    @Provides
    @Singleton
    fun provideMedicationNetworkRepository(
        apiService: MedicationApiService
    ): MedicationNetworkRepository =
        MedicationNetworkRepositoryImpl(apiService)
}
