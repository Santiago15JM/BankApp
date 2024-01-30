package com.sjm.bankapp.logic

import com.sjm.bankapp.config.RetrofitHelper
import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.BillQuote
import com.sjm.bankapp.logic.models.dao.ChangeEmailRequest
import com.sjm.bankapp.logic.models.dao.ChangePasswordRequest
import com.sjm.bankapp.logic.models.dao.ChangePhoneRequest
import com.sjm.bankapp.logic.models.dao.Entry
import com.sjm.bankapp.logic.models.dao.LoginRequest
import com.sjm.bankapp.logic.models.dao.LoginResponse
import com.sjm.bankapp.logic.models.dao.TransactionRequest
import com.sjm.bankapp.logic.models.dao.TransactionResponse
import retrofit2.Response
import java.time.LocalDateTime
import kotlin.random.Random

object BankEnd {
    private val api = RetrofitHelper.getInstance()

    suspend fun getTransactionHistory(from: Int = 0): List<Entry>? {
        return api.getTransactionHistory(LocalStorage.accountId, from).body()
    }

    suspend fun sendCash(transaction: TransactionRequest): Response<TransactionResponse> {
        return api.makeTransaction(transaction)
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

    suspend fun getBalance(): Long {
        return api.getBalance(LocalStorage.accountId)
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

    suspend fun changePassword(userId: Long, newPassword: String, oldPassword: String): Boolean {
        val response = api.changePassword(ChangePasswordRequest(userId, oldPassword, newPassword))
        return response.body()?.succeeded ?: false
    }
}
