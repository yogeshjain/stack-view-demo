package com.example.stackviewdemo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    fun printLog() {
        Log.e("test", "This is working")
    }
}