package com.sjm.bankapp.logic.models

import java.io.Serializable
import java.time.LocalDateTime

data class Entry(
    val operationId: String,
    val type: TransactionType,
    val amount: Int,
    val userId: Long,
    val accountId: Long,
    val date: LocalDateTime,
    val resultingBalance: Long,
    val id: Long,
) : Serializable
