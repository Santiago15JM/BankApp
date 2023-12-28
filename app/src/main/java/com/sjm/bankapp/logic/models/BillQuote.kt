package com.sjm.bankapp.logic.models

data class BillQuote(
    val id: Int,
    val shopId: Int,
    val shopName: String,
    val cost: Int,
)