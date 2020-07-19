package com.example.stackviewdemo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.stackviewdemo.R
import com.example.stackviewdemo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_loan_demo.setOnClickListener {
            startActivity(Intent(this, LoanDemoActivity::class.java))
        }

        btn_cc_demo.setOnClickListener {
            startActivity(Intent(this, CCDemoActivity::class.java))
        }
    }

}