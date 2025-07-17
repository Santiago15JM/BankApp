package com.sjm.bankapp.logic.models

import java.io.Serializable

data class Bill(
    val id: Long,
    val businessId: Long,
    val serviceId: String,
    val cost: Long,
    val status: BillState
) : Serializable