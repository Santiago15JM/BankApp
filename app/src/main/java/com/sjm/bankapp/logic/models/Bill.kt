package com.sjm.bankapp.logic.models

import kotlinx.serialization.Serializable

@Serializable
data class Bill(
    val id: Long,
    val businessId: Long,
    val serviceId: String,
    val cost: Long,
    val status: BillState
)