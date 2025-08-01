package com.sjm.bankapp.logic.dto.transaction

import com.sjm.bankapp.config.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TransactionResponse(
    val operationId: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime,
    val balance: Long
)
