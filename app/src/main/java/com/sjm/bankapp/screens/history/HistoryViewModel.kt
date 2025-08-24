package com.sjm.bankapp.screens.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.Session
import com.sjm.bankapp.logic.models.Entry
import com.sjm.bankapp.logic.models.Transaction
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    val history = mutableStateListOf<Entry>()
    var loadedInitial by mutableStateOf(false)
    var failed by mutableStateOf(false)
    var loading by mutableStateOf(false)
    var endOfHistory by mutableStateOf(false)
    private var page = 0

    init {
        viewModelScope.launch {
            try {
                val res = BankEnd.getTransactionHistory(Session.accountId) ?: return@launch
                history.addAll(res)
                if (res.count() < 10) endOfHistory = true

                page++
                loadedInitial = true
            } catch (e: Exception) {
                loadedInitial = true
                failed = true
            }
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            loading = true
            try {
                val res = BankEnd.getTransactionHistory(Session.accountId,page) ?: return@launch
                history.addAll(res)
                if (res.isEmpty() || res.count() < 10) endOfHistory = true
                page++
                loading = false
                failed = false
            } catch (e: Exception) {
                loading = false
                failed = true
            }
        }
    }

    fun getTransactionDetails(operationId: String, nextScreen: (Transaction) -> Unit) {
        viewModelScope.launch {
            val transaction = BankEnd.getTransactionDetails(operationId)
            if (transaction != null) nextScreen(transaction)
        }
    }

}
