package com.sjm.bankapp.logic.models.dto

data class LoginResponse(
    val token: String,
    val uid: Long,
    val aid: Long,
    val name: String,
    val phone: String,
)