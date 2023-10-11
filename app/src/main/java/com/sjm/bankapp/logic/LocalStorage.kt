package com.sjm.bankapp.logic

import android.content.Context
import androidx.room.Room

object LocalStorage {
    private lateinit var db: AppDatabase
    const val currentUser: Long = 1

    fun instantiateDB(context: Context) {
        if (!this::db.isInitialized) {
            db = Room.databaseBuilder(context, AppDatabase::class.java, "App.db").build()
        }
    }

    fun getSavedAccountsDao() = db.savedAccountsDao()

    fun getCurrentEmail() = "aaa@gmail.com" //TODO get/save in preferences

}
