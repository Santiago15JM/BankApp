package com.sjm.bankapp.logic.models

enum class TransactionType(val text: String) {
    DEPOSIT("Deposito"),
    WITHDRAW("Retiro"),
    TRANSFER_OUT("Transferencia"),
    TRANSFER_IN("Transferencia"),
    BILL("Factura"),
}
