package com.sjm.bankend.models

enum class TransactionState(val value: String) {
    PENDING("PENDIENTE"), FAILED("FALLIDA"), SUCCESS("COMPLETADA")
}