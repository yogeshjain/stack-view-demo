package com.example.stackviewdemo.stack.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stackviewdemo.model.PaymentPlan
import com.example.stackviewdemo.utils.toRounderAmount

class RadioSIViewModel : ViewModel() {
    var selectedItem: Pair<String, String?> = Pair("", null)
}