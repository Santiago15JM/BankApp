package com.sjm.bankapp.logic.models

import java.io.Serializable
import java.time.LocalDateTime

data class Transaction(
    val operationId: String,
    val amount: Int,
    val senderId: Long,
    val receiverId: Long,
    val date: LocalDateTime,
    val resultingBalance: Long,
) : Serializable // TODO Migrate to Parcelables
