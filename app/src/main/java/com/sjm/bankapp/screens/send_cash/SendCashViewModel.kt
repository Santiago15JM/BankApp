package com.sjm.bankapp.screens.send_cash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.LocalStorage
import com.sjm.bankapp.logic.models.SavedAccount
import com.sjm.bankapp.logic.models.Transaction
import com.sjm.bankapp.logic.models.TransactionType
import com.sjm.bankapp.logic.models.dao.TransactionResponse
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SendCashViewModel : ViewModel() {
    //Saved Account
    var savedAccounts: MutableList<SavedAccount> = mutableStateListOf()
    var usingSavedAccount by mutableStateOf(true)
    val selectedAccount by lazy { mutableStateOf(savedAccounts.first()) }

    //Non Saved Account
    var receiverID by mutableStateOf("")
    var amount by mutableStateOf("")
    var description by mutableStateOf("")
    var saveAccount by mutableStateOf(false)

    private var dao = LocalStorage.getSavedAccountsDao()

    init {
        viewModelScope.launch {
            dao.getAll().collect {
                savedAccounts.clear()
                savedAccounts.addAll(it)
            }
        }
    }

    fun onSendTransaction(next: (Transaction, TransactionResponse) -> Unit) {
        if (!usingSavedAccount && saveAccount) saveNewAccount(
            SavedAccount(receiverID.toLong(), description.ifBlank { receiverID })
        )

        val t = Transaction(
            type = TransactionType.TRANSFER_OUT,
            amount = amount.toInt(),
            senderId = LocalStorage.userId,
            receiverId = if (usingSavedAccount) selectedAccount.value.id else receiverID.toLong(),
            date = LocalDateTime.now(),
            currentBalance = 50000
        )

        val res = BankEnd.sendCash(t)
        next(t, res)
    }

    private fun saveNewAccount(newAccount: SavedAccount) {
        viewModelScope.launch {
            dao.upsert(newAccount)
        }
    }

    fun shouldEnableButton(): Boolean = when {
        amount.isEmpty() -> false
        usingSavedAccount and savedAccounts.isNotEmpty() -> true
        !usingSavedAccount and receiverID.isNotEmpty() -> true
        else -> false
    }
}
