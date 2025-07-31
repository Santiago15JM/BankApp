package com.sjm.bankapp.logic.dto.transaction

data class TransactionRequest(
    val amount: Int,
    val senderAId: Long,
    val receiverAId: Long,
)
