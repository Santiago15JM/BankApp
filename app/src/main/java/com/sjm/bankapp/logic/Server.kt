package com.sjm.bankapp.logic

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDateTime
import kotlin.random.Random

object Server {
    val ts = mutableStateListOf(
        Transaction(1, TransactionType.DEPOSIT, 56000, 2, 1, LocalDateTime.now()),
    )

    fun getTransactionHistory(from: Int = 0): MutableList<Transaction> {
        //Get history from x index to lazy load
        return ts
    }

    fun getBalance() = 10230000

    fun sendCash(transaction: Transaction): TransactionResponse {
        val res = TransactionResponse(Random.nextLong(), "Efectuada")
        transaction.id = res.transactionId
        ts.add(0, transaction)
        return res
    }

    fun payBill(bill: Bill) : TransactionResponse {
        Log.d("PAY_BILL", "Attempting Request")
        val res = TransactionResponse(Random.nextLong(), "Success")
        bill.id = res.transactionId
        ts.add(
            0, Transaction(
                bill.id,
                TransactionType.BILL,
                bill.cost,
                bill.customerId,
                bill.shopId,
                bill.date
            )
        )
        return res
    }

    fun fetchBill(billCode: Int, shopID: Int): BillQuote {
        return BillQuote(
            Random.nextInt(100, 500), shopID, "Name Shop", Random.nextInt(from = 0, until = 1000000)
        )
    }

    fun changeEmail(email: String) {
        Log.d("CHANGE EMAIL", "Changed email")
    }

    fun changePhone(phone: String) {
        Log.d("CHANGE PHONE", "Changed phone number")
    }
}
