package com.sjm.bankapp.logic.dto.auth

data class LoginResponse(
    val token: String,
    val uid: Long,
    val aid: Long,
    val name: String,
    val phone: String,
)