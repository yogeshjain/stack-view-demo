package com.example.stackviewdemo.stack.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stackviewdemo.utils.toRounderAmount

class AmountSIViewModel : ViewModel() {
    val loanAmountLiveData: MutableLiveData<Int> = MutableLiveData(0)
    var maxAmount = 0

    fun getMaxAmount(userId: String? = null): Int {
        if(userId != null) {
            //Fetch limit from an API or Repo
            maxAmount = 50000000
        }
        return maxAmount //Return dummy value for demo
    }

    fun setProgress(progress: Int) {
        if(progress < maxAmount) {
            loanAmountLiveData.postValue(progress.toRounderAmount())
        } else {
            loanAmountLiveData.postValue(progress)
        }
    }
}