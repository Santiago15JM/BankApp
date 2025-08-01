package com.sjm.bankapp.logic.models

import com.sjm.bankapp.config.LocalDateTimeSerializer
import com.sjm.bankend.models.TransactionState
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Transaction(
    val operationId: String,
    val amount: Int,
    val senderAccountId: Long,
    val receiverAccountId: Long,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime,
    val state: TransactionState,
)
