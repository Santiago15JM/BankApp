package com.sjm.bankapp.screens.settings

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.LocalStorage
import com.sjm.bankapp.logic.Preferences
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class SettingsViewModel : ViewModel() {
    var showChangeEmail by mutableStateOf(false)
    var showChangePhone by mutableStateOf(false)
    var showChangePassword by mutableStateOf(false)
    var showLoading by mutableStateOf(false)
    var showNetworkError by mutableStateOf(false)
    var showError by mutableStateOf(false)
    var showSuccess by mutableStateOf(false)
    var logout = false

    fun changeEmail(email: String, context: Context) {
        viewModelScope.launch {
            showLoading = true
            try {
                val res = BankEnd.changeEmail(LocalStorage.userId, email)
                if (res) {
                    Preferences.saveEmail(context, email)
                    logout = true
                    showSuccess = true
                } else {
                    showError = true
                }
            } catch (e: SocketTimeoutException) {
                showNetworkError = true
            }
            showLoading = false
        }
    }

    fun changePhone(phone: String, context: Context) {
        viewModelScope.launch {
            showLoading = true
            try {
                val res = BankEnd.changePhone(LocalStorage.userId, phone)
                if (res) {
                    Preferences.savePhone(context, phone)
                    showSuccess = true
                } else {
                    showError = true
                }
            } catch (e: SocketTimeoutException) {
                showNetworkError = true
            }
            showLoading = false
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            showLoading = true
            try {
                val res = BankEnd.changePassword(LocalStorage.userId, newPassword, oldPassword)
                if (res) {
                    logout = true
                    showSuccess = true
                } else {
                    showError = true
                }
            } catch (e: SocketTimeoutException) {
                showNetworkError = true
            }
            showLoading = false
        }
    }
}
