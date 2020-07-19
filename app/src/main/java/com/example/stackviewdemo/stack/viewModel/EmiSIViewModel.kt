package com.example.stackviewdemo.stack.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stackviewdemo.model.PaymentPlan
import com.example.stackviewdemo.utils.toRounderAmount

class EmiSIViewModel : ViewModel() {
    val planLiveData: MutableLiveData<List<PaymentPlan>> = MutableLiveData()
    var selectedPlan: PaymentPlan? = null
    var maxAmount = 0

    val durations = listOf(48, 36, 24, 12, 6, 3)

    fun getEmiPlans(amount: Int = 0): LiveData<List<PaymentPlan>> {
        var intrest = 24
        val list = mutableListOf<PaymentPlan>()

        durations.forEach {
            val actual = (amount*(1+(intrest/100.0))).toInt()
            if(actual/it > 1000) {
                list.add(PaymentPlan(actual / it, it, "mo", "months"))
            }
            intrest -= 4
        }

        planLiveData.postValue(list)
        return planLiveData
    }

    fun selectPlan(paymentPlan: PaymentPlan) {
        selectedPlan = paymentPlan
    }
}