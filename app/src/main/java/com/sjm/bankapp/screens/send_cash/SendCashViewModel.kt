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
import com.sjm.bankapp.logic.models.dao.TransactionRequest
import kotlinx.coroutines.launch

class SendCashViewModel : ViewModel() {
    private var dao = LocalStorage.getSavedAccountsDao()
    var amount by mutableStateOf("")

    //Saved Account
    var savedAccounts: MutableList<SavedAccount> = mutableStateListOf()
    var usingSavedAccount by mutableStateOf(true)
    val selectedAccount by lazy { mutableStateOf(savedAccounts.first()) }

    //Non Saved Account
    var receiverID by mutableStateOf("")
    var description by mutableStateOf("")
    var saveAccount by mutableStateOf(false)


    init {
        viewModelScope.launch {
            dao.getAll().collect {
                savedAccounts.clear()
                savedAccounts.addAll(it)
            }
        }
    }

    fun onSendTransaction(onSuccess: (Transaction) -> Unit, onError: (Int) -> Unit) {
        if (!usingSavedAccount && saveAccount) saveNewAccount(
            SavedAccount(receiverID.toLong(), description.ifBlank { receiverID })
        )

        val request = TransactionRequest(
            amount = amount.toInt(),
            senderId = LocalStorage.accountId,
            receiverId = if (usingSavedAccount) selectedAccount.value.id else receiverID.toLong()
        )

        viewModelScope.launch {
            val response = BankEnd.sendCash(request)

            if (response.isSuccessful) {
                val transaction = Transaction(
                    operationId = response.body()!!.operationId,
                    amount = request.amount,
                    senderId = request.senderId,
                    receiverId = request.receiverId,
                    date = response.body()!!.date,
                    resultingBalance = response.body()!!.balance
                )
                onSuccess(transaction)
            } else {
                onError(response.code())
            }
        }
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
