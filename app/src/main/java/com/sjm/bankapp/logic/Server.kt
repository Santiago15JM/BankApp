package com.sjm.bankapp.logic

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.sjm.bankapp.config.RetrofitHelper
import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.BillQuote
import com.sjm.bankapp.logic.models.Transaction
import com.sjm.bankapp.logic.models.TransactionType
import com.sjm.bankapp.logic.models.dao.ChangeEmailRequest
import com.sjm.bankapp.logic.models.dao.ChangePhoneRequest
import com.sjm.bankapp.logic.models.dao.LoginRequest
import com.sjm.bankapp.logic.models.dao.LoginResponse
import com.sjm.bankapp.logic.models.dao.TransactionResponse
import java.time.LocalDateTime
import kotlin.random.Random

object Server {
    private val api = RetrofitHelper.getInstance()

    private val ts = mutableStateListOf(
        Transaction(1, TransactionType.DEPOSIT, 56000, 2, 1, LocalDateTime.now(), 50000),
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

    fun payBill(bill: Bill): TransactionResponse {
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
                bill.date,
                50000
            )
        )
        return res
    }

    fun fetchBill(billCode: Int, shopID: Int): BillQuote {
        return BillQuote(
            Random.nextInt(100, 500), shopID, "Name Shop", Random.nextInt(from = 0, until = 1000000)
        )
    }


    suspend fun login(email: String, password: String): LoginResponse? {
        return api.login(LoginRequest(email, password)).body()
    }

    suspend fun changeEmail(userId: Long, email: String): Boolean {
        val response = api.changeEmail(ChangeEmailRequest(userId, email))
        return response.body()?.succeeded ?: false
    }

    suspend fun changePhone(userId: Long, phone: String): Boolean {
        val response = api.changePhone(ChangePhoneRequest(userId, phone))
        return response.body()?.succeeded ?: false
    }
}
