package com.example.stackviewdemo.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.example.stackviewdemo.R
import com.example.stackviewdemo.view.BaseStackActivity
import com.example.stackviewdemo.viewmodel.BaseStackViewModel
import com.example.stackviewdemo.viewmodel.CCDemoViewModel
import com.example.stackviewdemo.viewmodel.LoanDemoViewModel
import com.example.stackviewdemo.viewmodel.ViewAction
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_stack_demo.*


class CCDemoActivity : BaseStackActivity() {
    val ccViewModel: CCDemoViewModel by viewModels()
    override fun getViewModel(): BaseStackViewModel {
        return ccViewModel
    }
}