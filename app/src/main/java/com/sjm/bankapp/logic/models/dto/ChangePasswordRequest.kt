package com.sjm.bankapp.logic.models.dto

data class ChangePasswordRequest(val userId: Long, val oldPassword: String, val newPassword: String)