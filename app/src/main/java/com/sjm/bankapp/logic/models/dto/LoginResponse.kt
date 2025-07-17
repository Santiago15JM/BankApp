package com.sjm.bankapp.logic.models.dto

data class LoginResponse(
    val token: String,
    val id: Long,
    val name: String,
    val phone: String,
)