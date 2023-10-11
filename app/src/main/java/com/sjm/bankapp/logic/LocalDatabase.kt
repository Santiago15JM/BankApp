package com.sjm.bankapp.logic

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Upsert
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
    fun getAll(): Flow<List<SavedAccount>>

    @Upsert
    suspend fun upsert(accounts: SavedAccount)

//    @Upsert
//    suspend fun upsertAll(accounts: List<TransactionAccount>)

    @Delete
    suspend fun delete(account: SavedAccount)
}
