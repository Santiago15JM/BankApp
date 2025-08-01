package com.sjm.bankapp.navigation

import androidx.navigation3.runtime.NavKey
import com.sjm.bankapp.logic.dto.transaction.TransactionResponse
import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.Entry
import com.sjm.bankapp.logic.models.Transaction
import kotlinx.serialization.Serializable

@Serializable
object LoginKey : NavKey

@Serializable
object HomeKey : NavKey

@Serializable
object TransactionHistoryKey : NavKey

@Serializable
data class TransactionDetailsKey(val transaction: Transaction, val entry: Entry) : NavKey

@Serializable
object SendMoneyKey : NavKey

@Serializable
data class PostPaymentKey(val transaction: Transaction) : NavKey

@Serializable
object PayBillKey : NavKey

@Serializable
data class PostBillKey(val bill: Bill, val transaction: TransactionResponse, val businessName: String) : NavKey

@Serializable
object SettingsKey : NavKey

@Serializable
object ManageContactsKey : NavKey