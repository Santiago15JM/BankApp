package com.sjm.bankapp.logic.models.dto

data class RegisterTokenRequest(
    val accountId: Long,
    val token: String
)
