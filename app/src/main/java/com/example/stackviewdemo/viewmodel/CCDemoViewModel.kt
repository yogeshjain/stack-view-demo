package com.example.stackviewdemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stackviewdemo.model.PaymentPlan
import com.example.stackviewdemo.stack.view.AmountSItem
import com.example.stackviewdemo.stack.view.EmiSItem
import com.example.stackviewdemo.stack.view.RadioSItem
import com.example.stackviewdemo.view.abstract.StackedView

class CCDemoViewModel : BaseStackViewModel() {
    private var cardType = Pair<String, String?>("", null)
    private var bank = Pair<String, String?>("", null)

    override fun getValueAndValidate(stackedView: StackedView, pos: Int): Boolean {
        when (pos) {
            1 -> { //card type
                if (stackedView is RadioSItem) {
                    cardType = stackedView.getSelectedItem()
                    return true
                }
                return false
            }
            2 -> { //bank
                if (stackedView is RadioSItem) {
                    bank = stackedView.getSelectedItem()
                    return true
                }
            }
        }
        return false
    }

    override fun getItemAtPos(pos: Int): StackedView? {
        when (pos) {
            0 -> { //Base view
                return RadioSItem.getInstance(
                    listOf(
                        Pair("Rupay", "Domestic"),
                        Pair("Rupay", "International"),
                        Pair("Mastercard", null),
                        Pair("VISA", null)
                    ),
                    "Which type of credit card would you like?",
                    "Select card type below",
                    "Select Bank"
                )
            }
            1 -> { //Go to EMI transition
                val banks = getBanksFor(cardType.first)
                if(banks != null) {
                    return RadioSItem.getInstance(
                        banks.map { Pair<String, String?>(it, null) },
                        "Which bank do you prefer?",
                        "Selected bank will issue the credit card",
                        "Apply for Card"
                    )
                } else {
                    return null
                }
            }
        }
        return null
    }

    val bankMap = hashMapOf(
        "Rupay" to listOf("HDFC","SBI"),
        "Mastercard" to listOf("HDFC","SBI", "ICICI"),
        "VISA" to listOf("HDFC","Kotak Bank", "IDBI", "ICICI", "SBI")
    )

    private fun getBanksFor(cardType: String): List<String>? {
        if(bankMap.containsKey(cardType)) {
            return bankMap[cardType]
        } else {
            return null
        }
    }
}