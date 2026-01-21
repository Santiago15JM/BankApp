package com.sjm.bankapp.logic.models

enum class TransactionState(val value: String) {
    PENDING("PENDIENTE"), FAILED("FALLIDA"), SUCCESS("COMPLETADA")
}