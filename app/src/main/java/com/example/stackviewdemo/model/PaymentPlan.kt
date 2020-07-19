package com.example.stackviewdemo.model

import com.example.stackviewdemo.utils.cashify

data  class PaymentPlan (
    val amount: Int,
    val duration: Int,
    val amount_unit: String,
    val duration_unit: String
) {
    fun getPlanAmountText(): String = "₹${amount.cashify()} /$amount_unit"
    fun getDurationText():String = "$duration $duration_unit"
}