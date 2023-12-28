package com.sjm.bankapp.logic

import android.content.Context
import androidx.room.Room

object LocalStorage {
    private lateinit var db: AppDatabase
    var userId: Long = 0
        private set
    var authToken: String = ""
        private set
    var userName: String = ""
        private set
    var userEmail: String = ""
        private set
    var userPhone: String = ""
        private set

    fun instantiateDB(context: Context) {
        if (!this::db.isInitialized) {
            db = Room.databaseBuilder(context, AppDatabase::class.java, "App.db").build()
        }
    }

    fun getSavedAccountsDao() = db.savedAccountsDao()

    suspend fun loadDetails(context: Context) {
        userId = Preferences.getUserId(context)!!
        authToken = Preferences.getAuthToken(context)!!
        userName = Preferences.getUserName(context)!!
        userEmail = Preferences.getUserEmail(context)!!
        userPhone = Preferences.getUserPhone(context)!!
    }

    suspend fun saveLoginDetails(
        context: Context, token: String, id: Long, name: String, email: String, phone: String
    ) {
        Preferences.saveUserId(context, id)
        Preferences.saveAuthToken(context, token)
        Preferences.saveUserName(context, name)
        Preferences.saveEmail(context, email)
        Preferences.savePhone(context, phone)

        userId = id
        authToken = token
        userName = name
        userEmail = email
        userPhone = phone
    }
}
