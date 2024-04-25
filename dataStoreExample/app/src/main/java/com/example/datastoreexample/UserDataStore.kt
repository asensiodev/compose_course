package com.example.datastoreexample

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStore(private val context: Context) {

    val getEmail: Flow<String> = context.dataStore.data
        .map { userData -> userData[USER_EMAIL] ?: "" }

    suspend fun saveEmail(email: String) {
        context.dataStore.edit { userData ->
            userData[USER_EMAIL] = email
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userData")
        val USER_EMAIL = stringPreferencesKey("user_email")
    }
}