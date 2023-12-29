package com.sjm.bankapp.logic.models.dao

data class ChangePasswordRequest(val userId: Long, val oldPassword: String, val newPassword: String)