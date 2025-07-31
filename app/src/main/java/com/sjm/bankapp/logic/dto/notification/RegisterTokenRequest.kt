package com.sjm.bankapp.logic.dto.notification

data class RegisterTokenRequest(
    val accountId: Long,
    val token: String
)
