package com.sjm.bankapp.logic.models

enum class TransactionType(val text: String) {
    DEPOSIT("Deposito"),
    WITHDRAW("Retiro"),
    TRANSFER_OUT("Transferencia saliente"),
    TRANSFER_IN("Transferencia entrante"),
    BILL("Factura"),
}
