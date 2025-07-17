package com.sjm.bankapp.logic.models

import com.sjm.bankend.models.TransactionState
import java.io.Serializable
import java.time.LocalDateTime

data class Transaction(
    val operationId: String,
    val amount: Int,
    val senderId: Long,
    val receiverId: Long,
    val date: LocalDateTime,
    val state: TransactionState,
) : Serializable // TODO Migrate to Parcelables
