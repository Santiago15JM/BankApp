package com.sjm.bankapp.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.LocalStorage
import com.sjm.bankapp.screens.destinations.HomeDestination
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

    fun logIn(navigator: DestinationsNavigator, context: Context) {
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
                            context, response.token, response.id, response.name, email, response.phone
                        )
                        navigator.navigate(HomeDestination)
                        email = ""
                        password = ""
                    }
                } catch (e: SocketTimeoutException) {
                    showNetworkError = true
                }

                showLoading = false
            }
        }
    }
}
