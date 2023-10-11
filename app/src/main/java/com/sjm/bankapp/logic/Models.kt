package com.sjm.bankapp.logic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime

data class Account(
    val id: Int, val user: String = "", val password: String = "", var balance: Number = 0
) : Serializable

object AccountList { //TODO: Login system
    val accounts = mutableListOf(
        Account(1, "san", "contra")
    )
}

@Entity
data class SavedAccount(
    @ColumnInfo(name = "id") var id: Long,
    @ColumnInfo(name = "description") var description: String,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)

data class Transaction(
    var id: Long = 0,
    val type: TransactionType,
    val amount: Int,
    val senderId: Long,
    val receiverId: Long,
    val date: LocalDateTime
) : Serializable //TODO Migrate to Parcelables

data class TransactionResponse(
    val transactionId: Long,
    val transactionResult: String,
) : Serializable

data class Bill(
    var id: Long = 0, // != than bill code, obtained from fetched bill info
    val customerId: Long,
    val shopId: Long,
    val cost: Int,
    val date: LocalDateTime,
) : Serializable

enum class TransactionType(val text: String) {
    DEPOSIT("Deposito"),
    WITHDRAW("Retiro"),
    TRANSFER_OUT("Transferencia"),
    TRANSFER_IN("Transferencia"),
    BILL("Factura"),
}

data class BillQuote(
    val id: Int,
    val shopId: Int,
    val shopName: String,
    val cost: Int,
)
