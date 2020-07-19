package com.example.stackviewdemo.stack.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stackviewdemo.R
import com.example.stackviewdemo.stack.viewModel.AmountSIViewModel
import com.example.stackviewdemo.utils.cashify
import com.example.stackviewdemo.view.abstract.StackedView
import kotlinx.android.synthetic.main.si_amount.*
import kotlinx.android.synthetic.main.si_amount.view.*

class AmountSItem() : StackedView() {
    val amountSIViewModel: AmountSIViewModel by viewModels()

    lateinit var userId: String

    private constructor(userId: String): this() {
        this.userId = userId
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        isExpanded = true
        return inflater.inflate(R.layout.si_amount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val maxAmount = amountSIViewModel.getMaxAmount(userId)
        tv_amount_max.text = "₹${maxAmount.cashify()}"
        tv_amount_subtext.text = "Enter the amount you need up to ₹${maxAmount.cashify()}"
        sb_amount_loan.max = maxAmount
        sb_amount_loan.progress = 0
        sb_amount_loan.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                amountSIViewModel.setProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        amountSIViewModel.loanAmountLiveData.observe(activity as LifecycleOwner, Observer {
            tv_amount_loan.text = "₹${it.cashify()}"
            tv_amount_loan_collapsed.text = "₹${it.cashify()}"
        })

    }

    override fun getBottomActionText() = "Proceed to EMI Selection"

    override fun onBottomItemClick() {
        //No action needed here
    }

    override fun getCollapsedView(): View {
        return cl_amount_collapsed
    }

    override fun getExpandedView(): View {
        return cl_amount_expanded
    }

    fun getSelectedAmount() = amountSIViewModel.loanAmountLiveData.value

    companion object{
        fun getInstance(userId: String): AmountSItem {
            return AmountSItem(userId)
        }
    }

}