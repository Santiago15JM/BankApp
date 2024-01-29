package com.sjm.bankapp.logic.models.dao

import com.sjm.bankapp.logic.models.TransactionType
import java.time.LocalDateTime

data class Entry(
    val operationId: String,
    val type: TransactionType,
    val amount: Int,
    val accountId: Long,
    val date: LocalDateTime,
    val balanceAtTime: Long,
    val id: Long,
)