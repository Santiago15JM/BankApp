package com.sjm.bankapp.logic

fun String.isValidEmail(): Boolean {
    return this.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
