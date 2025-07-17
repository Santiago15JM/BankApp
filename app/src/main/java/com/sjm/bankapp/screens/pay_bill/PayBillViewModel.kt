package com.sjm.bankapp.screens.pay_bill

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjm.bankapp.logic.BankEnd
import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.Business
import com.sjm.bankapp.logic.models.dto.TransactionResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PayBillViewModel : ViewModel() {
    var selectedBusiness by mutableStateOf<Business?>(null)
    var billCode by mutableStateOf("")
    var businessList = mutableListOf<Business>()
    var bill by mutableStateOf<Bill?>(null)

    var state by mutableStateOf(State.INITIAL)
    var fetchCostJob: Job? = null

    fun fetchBusinesses() {
        viewModelScope.launch {
            val businesses = BankEnd.getBusinesses()
            businessList.addAll(businesses)
        }
    }

    private suspend fun getBill() {
        bill = BankEnd.fetchBill(selectedBusiness!!.id, billCode)

        state = if (bill == null) {
            State.NOT_FOUND
        } else {
            State.FOUND
        }
    }

    fun startFetchJob() {
        fetchCostJob?.cancel()

        if (selectedBusiness == null || billCode.isEmpty()) {
            state = State.NOT_FOUND
            return
        }

        fetchCostJob = viewModelScope.launch {
            state = State.FETCHING
            delay(1_000) // Debounce time
            getBill()
        }
    }

    fun payBill(next: (Bill, TransactionResponse) -> Unit) {
        if (selectedBusiness != null && billCode.isNotEmpty() && state == State.FOUND && bill != null) {
            viewModelScope.launch {
                val res = BankEnd.payBill(bill!!)
                next(bill!!, res)
            }
        }
    }

    fun shouldEnableButton() =
        selectedBusiness != null && billCode.isNotEmpty() && state == State.FOUND
}

enum class State {
    INITIAL, FETCHING, FOUND, NOT_FOUND
}