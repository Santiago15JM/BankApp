package com.sjm.bankapp.logic.dto.transaction

import java.io.Serializable
import java.time.LocalDateTime

data class TransactionResponse(
    val operationId: String,
    val date: LocalDateTime,
    val balance: Long
) : Serializable
