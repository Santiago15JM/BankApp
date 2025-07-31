package com.sjm.bankapp.logic.dto.auth

data class ChangePasswordRequest(val userId: Long, val oldPassword: String, val newPassword: String)