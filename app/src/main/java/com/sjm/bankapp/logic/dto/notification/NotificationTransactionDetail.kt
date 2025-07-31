package com.sjm.bankapp.logic.dto.notification

import java.math.BigDecimal

data class NotificationTransactionDetail(
    val amount: BigDecimal,
    val senderName: String
)
