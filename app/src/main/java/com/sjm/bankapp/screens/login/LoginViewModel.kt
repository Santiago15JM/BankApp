package com.sjm.bankapp.screens.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.FCM
import com.sjm.bankapp.logic.Session
import com.sjm.bankapp.logic.dto.auth.SessionDetails
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var showLoading by mutableStateOf(false)
    var showBadCredentials by mutableStateOf(false)
    var showNetworkError by mutableStateOf(false)

    /*TODO
    init
    if(Passes fingerprint)
    Server.login(LS.getUser, LS.getPassword)
    * */

    fun logIn(next: () -> Unit, context: Context) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                context, "Debes ingresar tus credenciales", Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModelScope.launch {
                showLoading = true

                try {
                    val response = BankEnd.login(email, password)
                    if (response == null) {
                        Toast.makeText(context, "Credenciales invalidas", Toast.LENGTH_SHORT).show()
                        showBadCredentials = true
                    } else {
                        val sessionDetails = SessionDetails(response, email)
                        onLoginSuccess(sessionDetails, next, context)
                    }
                } catch (_: IOException) {
                    showNetworkError = true
                }

                showLoading = false
            }
        }
    }

    suspend fun onLoginSuccess(sessionDetails: SessionDetails, next: () -> Unit, context: Context) {
        Session.saveSessionDetails(
            context,
            sessionDetails
        )

        Session.startTimeoutJob()
        next()
        email = ""
        password = ""
        sendNotificationToken()
    }

    suspend fun sendNotificationToken() {
        val token = FCM.getFCMToken()
        if (token == null) return

        val res = BankEnd.sendFCMToken(Session.userId, token)

        if (res != 200) Log.e("LOGIN", "Couldn't register notifications token $res")
        else Log.i("LOGIN", "Successfully registered notifications token")
    }
}
