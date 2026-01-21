package com.sjm.bankapp.screens.send_cash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.Session
import com.sjm.bankapp.logic.dto.transaction.TransactionRequest
import com.sjm.bankapp.logic.models.SavedAccount
import com.sjm.bankapp.logic.models.Transaction
import com.sjm.bankapp.logic.models.TransactionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SendCashViewModel : ViewModel() {
    private var dao = Session.getSavedAccountsDao()
    var amount by mutableStateOf("")
    var funds = 0L
        private set

    //Saved Account
    var savedAccounts: MutableList<SavedAccount> = mutableStateListOf()
    var usingSavedAccount by mutableStateOf(true)
    var selectedAccount by mutableStateOf<SavedAccount?>(null)

    //Non Saved Account
    var receiverID by mutableStateOf("")
    var description by mutableStateOf("")
    var saveAccount by mutableStateOf(false)


    init {
        viewModelScope.launch(Dispatchers.IO) {
            funds = BankEnd.getBalance()
            val accounts = dao.getAll()
            savedAccounts.clear()
            savedAccounts.addAll(accounts)
            selectedAccount = savedAccounts.firstOrNull()
        }
    }

    fun onSendTransaction(onSuccess: (Transaction) -> Unit, onError: (Int) -> Unit) {
        if (!usingSavedAccount && saveAccount) saveNewAccount(
            SavedAccount(receiverID.toLong(), description.ifBlank { receiverID })
        )

        val request = TransactionRequest(
            amount = amount.toInt(),
            senderAId = Session.accountId,
            receiverAId = if (usingSavedAccount) selectedAccount!!.aId else receiverID.toLong()
        )

        viewModelScope.launch {
            val response = BankEnd.sendCash(request)

            if (response.isSuccessful) {
                val transaction = Transaction(
                    operationId = response.body()!!.operationId,
                    amount = request.amount,
                    senderAccountId = request.senderAId,
                    receiverAccountId = request.receiverAId,
                    date = response.body()!!.date,
                    state = TransactionState.SUCCESS,
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

    fun isAmountInvalid(): Boolean = if (amount.isBlank()) false else amount.toLong() > funds

    fun shouldEnableButton(): Boolean = when {
        amount.isEmpty() -> false
        amount.toLong() > funds -> false
        usingSavedAccount and (selectedAccount == null || selectedAccount!!.aId == Session.accountId) -> false
        usingSavedAccount and savedAccounts.isNotEmpty() -> true
        !usingSavedAccount and (receiverID == Session.accountId.toString()) -> false
        !usingSavedAccount and receiverID.isNotEmpty() -> true
        else -> false
    }
}
