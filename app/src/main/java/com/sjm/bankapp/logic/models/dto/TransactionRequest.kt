package com.sjm.bankapp.logic.models.dto

data class TransactionRequest(
    val amount: Int,
    val senderAId: Long,
    val receiverAId: Long,
)
