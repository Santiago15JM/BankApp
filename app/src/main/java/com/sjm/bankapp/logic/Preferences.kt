package com.sjm.bankapp.logic

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "details")

object Preferences {

    suspend fun saveAuthToken(context: Context, token: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("auth_token")] = token
        }
    }

    suspend fun getAuthToken(context: Context): String? {
        return context.dataStore.data.first()[stringPreferencesKey("auth_token")]
    }

    suspend fun saveAccountId(context: Context, id: Long) {
        context.dataStore.edit { preferences ->
            preferences[longPreferencesKey("account_id")] = id
        }
    }

    suspend fun getAccountId(context: Context): Long? {
        return context.dataStore.data.first()[longPreferencesKey("account_id")]
    }

    suspend fun saveUserName(context: Context, name: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("user_name")] = name
        }
    }

    suspend fun getUserName(context: Context): String? {
        return context.dataStore.data.first()[stringPreferencesKey("user_name")]
    }

    suspend fun saveEmail(context: Context, email: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("user_email")] = email
        }
    }

    suspend fun getUserEmail(context: Context): String? {
        return context.dataStore.data.first()[stringPreferencesKey("user_email")]
    }

    suspend fun savePhone(context: Context, phone: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("user_phone")] = phone
        }
    }

    suspend fun getUserPhone(context: Context): String? {
        return context.dataStore.data.first()[stringPreferencesKey("user_phone")]
    }
}