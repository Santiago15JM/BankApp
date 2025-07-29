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
import com.sjm.bankapp.logic.LocalStorage
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

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
                        LocalStorage.saveLoginDetails(
                            context,
                            response.token,
                            response.uid,
                            response.aid,
                            response.name,
                            email,
                            response.phone
                        )
                        next()
                        email = ""
                        password = ""
                        sendNotificationToken()
                    }
                } catch (_: SocketTimeoutException) {
                    showNetworkError = true
                }

                showLoading = false
            }
        }
    }

    suspend fun sendNotificationToken() {
        val token = FCM.getFCMToken()
        if (token == null) return

        val res = BankEnd.sendFCMToken(LocalStorage.userId, token)

        if (res != 200) Log.e("LOGIN", "Couldn't register notifications token $res")
        else Log.i("LOGIN", "Successfully registered notifications token")
    }
}
