package com.sjm.bankapp.screens.pay_bill

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjm.bankapp.logic.Bill
import com.sjm.bankapp.logic.BillQuote
import com.sjm.bankapp.logic.LocalStorage
import com.sjm.bankapp.logic.Server
import com.sjm.bankapp.logic.TransactionResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.random.Random

class PayBillViewModel : ViewModel() {
    var shopID by mutableStateOf("")
    var billCode by mutableStateOf("")
    var quote: BillQuote? = null
    var cost by mutableStateOf(0)

    var fetchCostJob: Job by mutableStateOf(viewModelScope.launch { cancel() })
    var isQuoteObtained by mutableStateOf(false)
    var isFetchingQuote by mutableStateOf(false)
        private set

    fun onPayBill(next: (Bill, TransactionResponse) -> Unit) {
        if (shopID.isNotEmpty() && billCode.isNotEmpty() && !fetchCostJob.isActive) {
            val bill = Bill(
                customerId = LocalStorage.currentUser,
                shopId = Random.nextLong(),
                cost = cost,
                date = LocalDateTime.now()
            )
            val res = Server.payBill(bill)
            next(bill, res)
        }
    }

    private suspend fun getBillQuote() {
        if (shopID.isEmpty() or billCode.isEmpty()) return

        isFetchingQuote = true
        delay(200) // "Fetch delay"

        quote = Server.fetchBill(billCode.toInt(), shopID.toInt())
        cost = quote!!.cost

        isFetchingQuote = false
        isQuoteObtained = true

    }

    fun startFetchJob() {
        isQuoteObtained = false
        fetchCostJob.cancel()
        fetchCostJob = viewModelScope.launch {
            delay(2_000)
            getBillQuote()
        }
    }

    fun shouldEnableButton() = shopID.isNotEmpty() && billCode.isNotEmpty() && isQuoteObtained
}
