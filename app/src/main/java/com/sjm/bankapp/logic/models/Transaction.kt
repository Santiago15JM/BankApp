package com.sjm.bankapp.logic.models

import java.io.Serializable
import java.time.LocalDateTime

data class Transaction(
    var id: Long = 0,
    val type: TransactionType,
    val amount: Int,
    val senderId: Long,
    val receiverId: Long,
    val date: LocalDateTime,
    val currentBalance: Long
) : Serializable //TODO Migrate to Parcelables
