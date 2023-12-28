package com.sjm.bankapp.logic.models

import java.io.Serializable
import java.time.LocalDateTime

data class Bill(
    var id: Long = 0, // != than bill code, obtained from fetched bill info
    val customerId: Long,
    val shopId: Long,
    val cost: Int,
    val date: LocalDateTime,
) : Serializable