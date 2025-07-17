package com.sjm.bankapp.logic.models.dto

data class TransactionRequest(
    val amount: Int,
    val senderId: Long,
    val receiverId: Long,
)
