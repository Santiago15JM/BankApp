package com.sjm.bankapp.logic.models.dto

import java.math.BigDecimal

data class NotificationTransactionDetail(
    val amount: BigDecimal,
    val senderName: String
)
