package com.sjm.bankapp.navigation

import androidx.navigation3.runtime.NavKey
import com.sjm.bankapp.logic.dto.transaction.TransactionResponse
import com.sjm.bankapp.logic.models.Bill
import com.sjm.bankapp.logic.models.Entry
import com.sjm.bankapp.logic.models.Transaction
import kotlinx.serialization.Serializable

typealias NavType = NavKey

@Serializable
object LoginKey : NavType

@Serializable
object HomeKey : NavType

@Serializable
object TransactionHistoryKey : NavType

@Serializable
data class TransactionDetailsKey(val transaction: Transaction, val entry: Entry) : NavType

@Serializable
object SendMoneyKey : NavType

@Serializable
data class PostPaymentKey(val transaction: Transaction) : NavType

@Serializable
object PayBillKey : NavType

@Serializable
data class PostBillKey(val bill: Bill, val transaction: TransactionResponse, val businessName: String) : NavType

@Serializable
object SettingsKey : NavType

@Serializable
object ManageContactsKey : NavType