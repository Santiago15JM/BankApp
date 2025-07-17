package com.sjm.bankapp.logic.models

enum class BillState(val state: String) {
    PENDING("PENDIENTE"),
    PAID("PAGADO"),
    FAILED("FALLIDO")
}