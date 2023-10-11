package com.sjm.bankapp.screens.history

import androidx.lifecycle.ViewModel
import com.sjm.bankapp.logic.Server

class HistoryViewModel: ViewModel() {
    val history = Server.getTransactionHistory()

    //To be continued
}
