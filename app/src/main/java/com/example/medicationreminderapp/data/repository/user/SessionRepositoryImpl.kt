package com.example.medicationreminderapp.data.repository.user

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "session_prefs")

@Singleton
class SessionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SessionRepository {

    private companion object {
        val LOGGED_IN_USER_ID = intPreferencesKey("logged_in_user_id")
    }

    override suspend fun saveLoggedInUserId(userId: Int) {
        Log.d("TEST", "RADI")
        context.dataStore.edit { preferences ->
            preferences[LOGGED_IN_USER_ID] = userId
        }
    }

    override fun observeLoggedInUserId(): Flow<Int?> {
        Log.d("TEST", "RADI")
        return context.dataStore.data.map { preferences ->
            preferences[LOGGED_IN_USER_ID]
        }
    }

    override suspend fun clearSession() {
        Log.d("TEST", "RADI")
        context.dataStore.edit { preferences ->
            preferences.remove(LOGGED_IN_USER_ID)
        }
    }
}