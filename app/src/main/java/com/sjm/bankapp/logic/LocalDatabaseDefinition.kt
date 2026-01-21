package com.sjm.bankapp.logic

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Upsert
import com.sjm.bankapp.logic.models.SavedAccount
import kotlinx.coroutines.flow.Flow

@Database(
    entities = [SavedAccount::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedAccountsDao(): SavedAccountsDao
}

@Dao
interface SavedAccountsDao {
    @Query("SELECT * FROM SavedAccount")
    fun getAll(): List<SavedAccount>

    @Query("SELECT * FROM SavedAccount")
    fun getAllFlow(): Flow<List<SavedAccount>>

    @Upsert
    suspend fun upsert(accounts: SavedAccount)

    @Delete
    suspend fun delete(account: SavedAccount)
}
