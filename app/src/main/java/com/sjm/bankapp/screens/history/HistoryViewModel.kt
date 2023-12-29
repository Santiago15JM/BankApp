package com.sjm.bankapp.screens.history

import androidx.lifecycle.ViewModel
import com.sjm.bankapp.logic.BankEnd

class HistoryViewModel: ViewModel() {
    val history = BankEnd.getTransactionHistory()

    //To be continued
}
