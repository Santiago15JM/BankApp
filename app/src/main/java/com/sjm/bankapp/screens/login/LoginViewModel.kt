package com.sjm.bankapp.screens.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.sjm.bankapp.logic.AccountList
import com.sjm.bankapp.screens.destinations.HomeDestination

class LoginViewModel : ViewModel() {
    var user by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun updateUser(user: String) {
        this.user = user
    }

    fun updatePassword(password: String) { //TODO: Why?
        this.password = password
    }

    fun logIn(navigator: DestinationsNavigator, context: Context) { //TODO
        navigator.navigate(HomeDestination(AccountList.accounts[0]))
//        if (user.isEmpty() || password.isEmpty()) Toast.makeText(
//            context, "Debes ingresar tus credenciales", Toast.LENGTH_SHORT
//        ).show()
//        else {
//            val userAccount = AccountList.accounts.find { it.user == user && it.password == password }
//            if (userAccount == null) Toast.makeText(context, "Cuenta invalida", Toast.LENGTH_SHORT).show()
//            else {
//                navigator.navigate(HomeDestination(userAccount))
//                user = ""
//                password = ""
//            }
//        }
    }
}
