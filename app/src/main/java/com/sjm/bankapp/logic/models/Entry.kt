package com.sjm.bankapp.logic.models

import com.sjm.bankapp.config.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Entry(
    val operationId: String,
    val type: TransactionType,
    val amount: Int,
    val userId: Long,
    val accountId: Long,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime,
    val resultingBalance: Long,
    val id: Long,
)
