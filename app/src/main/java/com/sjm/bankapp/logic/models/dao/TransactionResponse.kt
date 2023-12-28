package com.sjm.bankapp.logic.models.dao

import java.io.Serializable

data class TransactionResponse(
    val transactionId: Long,
    val transactionResult: String,
) : Serializable