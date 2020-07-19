package com.example.stackviewdemo.stack.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stackviewdemo.R
import com.example.stackviewdemo.stack.viewModel.EmiSIViewModel
import com.example.stackviewdemo.view.abstract.StackedView
import com.example.stackviewdemo.view.adapter.PaymentPlanAdapter
import kotlinx.android.synthetic.main.si_emi.*

class EmiSItem() : StackedView() {
    val emiViewModel: EmiSIViewModel by viewModels()
    var planAdapter: PaymentPlanAdapter? = null

    var amount: Int = 0

    private constructor(amount: Int): this() {
        this.amount = amount
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        isExpanded = true
        return inflater.inflate(R.layout.si_emi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emiViewModel.getEmiPlans(amount).observe(this, Observer {
            if(planAdapter == null) {
                val lm = GridLayoutManager(activity, 2)
                rv_emi.layoutManager = lm
                planAdapter = PaymentPlanAdapter(it) { plan ->
                    emiViewModel.selectPlan(plan)
                    tv_emi_amount.text = plan.getPlanAmountText()
                    tv_emi_duration.text = plan.getDurationText()
                }
                rv_emi.adapter = planAdapter
            } else {
                planAdapter?.setData(it)
                planAdapter?.notifyDataSetChanged()
            }
        })

    }

    override fun getBottomActionText() = "Select your bank account"

    override fun onBottomItemClick() {
        //No action needed here
    }

    override fun getCollapsedView(): View {
        return cl_emi_collapsed
    }

    override fun getExpandedView(): View {
        return cl_emi_expanded
    }

    fun getSelectedPlan() = emiViewModel.selectedPlan

    companion object{
        fun getInstance(amount: Int): EmiSItem {
            return EmiSItem(amount)
        }
    }

}