package com.sjm.bankapp.logic.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedAccount(
    @ColumnInfo(name = "id") var id: Long,
    @ColumnInfo(name = "description") var description: String,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)
