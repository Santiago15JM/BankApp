package com.sjm.bankapp.logic

import com.sjm.bankapp.config.RetrofitHelper
import com.sjm.bankapp.logic.dto.auth.ChangeEmailRequest
import com.sjm.bankapp.logic.dto.auth.ChangePasswordRequest
import com.sjm.bankapp.logic.dto.auth.ChangePhoneRequest
import com.sjm.bankapp.logic.dto.auth.LoginRequest
import com.sjm.bankapp.logic.dto.auth.LoginResponse
import com.sjm.bankapp.logic.dto.notification.NotificationTransactionDetail
import com.sjm.bankapp.logic.dto.notification.RegisterTokenRequest
import com.sjm.bankapp.logic.dto.transaction.TransactionRequest
import com.sjm.bankapp.logic.dto.transaction.TransactionResponse
import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.Business
import com.sjm.bankapp.logic.models.Entry
import com.sjm.bankapp.logic.models.Transaction
import retrofit2.Response

object BankEnd {
    private val api = RetrofitHelper.getInstance()

    suspend fun getTransactionHistory(accountId: Long, from: Int = 0): List<Entry>? {
        return api.getTransactionHistory(accountId, from).body()
    }

    suspend fun sendCash(transaction: TransactionRequest): Response<TransactionResponse> {
        return api.makeTransaction(transaction)
    }

    suspend fun getBusinesses(): List<Business> {
        return api.getBusinesses().body()!!
    }

    suspend fun fetchBill(businessId: Long, serviceId: String): Bill? {
        return api.getBill(businessId, serviceId).body()
    }

    suspend fun payBill(bill: Bill): Response<TransactionResponse> {
        return api.payBill(bill)
    }

    suspend fun getBalance(): Long {
        return api.getBalance(Session.userId)
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

    suspend fun getTransactionDetails(operationId: String): Transaction? {
        return api.getTransactionDetails(operationId).body()
    }

    suspend fun sendFCMToken(accountId: Long, token: String): Int {
        return api.sendFCMToken(RegisterTokenRequest(accountId, token)).code()
    }

    suspend fun getTransactionNotificationDetail(operationId: String): NotificationTransactionDetail {
        return api.getTransactionDetail(operationId).body()!!
    }
}
