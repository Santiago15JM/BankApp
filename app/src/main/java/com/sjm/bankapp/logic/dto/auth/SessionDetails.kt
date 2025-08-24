package com.sjm.bankapp.logic.dto.auth

data class SessionDetails(
    val token: String,
    val uid: Long,
    val aid: Long,
    val name: String,
    val email: String,
    val phone: String,
) {
    constructor(loginResponse: LoginResponse, email: String) : this(
        loginResponse.token,
        loginResponse.uid,
        loginResponse.aid,
        loginResponse.name,
        email,
        loginResponse.phone
    )
}
