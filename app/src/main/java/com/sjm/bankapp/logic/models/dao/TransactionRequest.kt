package com.sjm.bankapp.logic.models.dao

data class TransactionRequest(
    val amount: Int,
    val senderId: Long,
    val receiverId: Long,
)
