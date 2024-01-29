package com.sjm.bankapp.screens.history

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.models.dao.Entry
import kotlinx.coroutines.launch

class HistoryViewModel: ViewModel() {
    val history = mutableStateListOf<Entry>()

    init {
        viewModelScope.launch {
            BankEnd.getTransactionHistory()?.let { history.addAll(it) }
        }
    }

    //TODO Load more pages
}
