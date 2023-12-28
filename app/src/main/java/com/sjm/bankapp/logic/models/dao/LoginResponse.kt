package com.sjm.bankapp.logic.models.dao

data class LoginResponse(
    val token: String,
    val id: Long,
    val name: String,
    val phone: String,
    val balance: Long,
)