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
    var state by mutableStateOf(HistoryScreenState.EMPTY)
    private var page = 0

    init {
        loadMore()
    }

    private fun loadTransactions(page: Int) {
        viewModelScope.launch {
            state = HistoryScreenState.LOADING
            try {
                val res = BankEnd.getTransactionHistory(Session.accountId, page)
                if (res == null) {
                    state = HistoryScreenState.FAILED
                    return@launch
                }
                history.addAll(res)

                state =
                    if (res.isEmpty() || res.count() < 10) HistoryScreenState.END else HistoryScreenState.LOADED
            } catch (_: Exception) {
                state = HistoryScreenState.FAILED
            }
        }
    }

    fun loadMore() = loadTransactions(page++)

    fun getTransactionDetails(operationId: String, nextScreen: (Transaction) -> Unit) {
        viewModelScope.launch {
            val transaction = BankEnd.getTransactionDetails(operationId)
            if (transaction != null) nextScreen(transaction)
        }
    }

    enum class HistoryScreenState() {
        EMPTY, LOADED, LOADING, FAILED, END
    }
}
