package com.example.stackviewdemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stackviewdemo.model.PaymentPlan
import com.example.stackviewdemo.stack.view.AmountSItem
import com.example.stackviewdemo.stack.view.EmiSItem
import com.example.stackviewdemo.stack.view.RadioSItem
import com.example.stackviewdemo.view.abstract.StackedView

class LoanDemoViewModel : BaseStackViewModel() {
    private var amount = 0
    private var emiPlan: PaymentPlan? = null
    private var bankAccount = ""
    private val errorLiveData: MutableLiveData<String> = MutableLiveData()
    fun getErrorLiveData(): LiveData<String> = errorLiveData

    fun getUserId(): String {
        //Fetch and return user details from repository
        return "123"
    }

    override fun getValueAndValidate(stackedView: StackedView, pos: Int): Boolean {
        when (pos) {
            1 -> { //collect amount
                if (stackedView is AmountSItem) {
                    val amt = stackedView.getSelectedAmount() ?: 0
                    if (amt > 3000) {
                        amount = amt
                        return true
                    } else {
                        errorLiveData.postValue("Please select minimum ₹3000 loan amount")
                        return false
                    }
                }
                return false
            }
            2 -> { //get payment plan
                if (stackedView is EmiSItem) {
                    emiPlan = stackedView.getSelectedPlan()
                    return true
                }
            }
            3 -> { // get bank details
                if (stackedView is RadioSItem) {
                    bankAccount = stackedView.getSelectedItem().first
                    Log.e(
                        "Test", "Submit application for ₹$amount \n" +
                                "Selected plan: ${emiPlan?.getPlanAmountText()} for ${emiPlan?.getDurationText()} \n" +
                                "On account $bankAccount"
                    )
                    return true
                }
            }
        }
        return false
    }

    override fun getItemAtPos(pos: Int): StackedView? {
        when (pos) {
            0 -> { //Base view
                return AmountSItem.getInstance(getUserId())
            }
            1 -> { //Go to EMI transition
                return EmiSItem.getInstance(amount)
            }
            2 -> { //EMI to account transition
                return RadioSItem.getInstance(
                    listOf(
                        Pair("HDFC", "11223300000000099"),
                        Pair("State Bank of India", "876654331")
                    ),
                    "Where should we send the money?",
                    "Amount will be credited to bank account. EMI will be deducted from this bank account",
                    "Tap for 1-click KYC"
                )
            }
            3 -> { // Process final application
                return null
            }
        }
        return null
    }
}