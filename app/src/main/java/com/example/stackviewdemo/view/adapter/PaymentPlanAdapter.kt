package com.example.stackviewdemo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.stackviewdemo.R
import com.example.stackviewdemo.model.PaymentPlan

class PaymentPlanAdapter(
    planList: List<PaymentPlan>?,
    private val onSelect: (paymentPlan: PaymentPlan) -> Unit
) : RecyclerView.Adapter<PaymentPlanAdapter.ViewHolder>() {
    var plans = planList
    var selectedItem:View? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.row_plan, viewGroup, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return plans?.size!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, index: Int) {
        val plan = plans?.get(index)
        viewHolder.amount.text = plan?.getPlanAmountText()
        viewHolder.duration.text = "for ${plan?.getDurationText()}"
        plan?.let { viewHolder.root.setOnClickListener {
            selectedItem?.setBackgroundResource(R.drawable.plan_background_unselected)
            viewHolder.root.setBackgroundResource(R.drawable.plan_background_selected)
            selectedItem = viewHolder.root
            onSelect(plan)
        } }

        if(selectedItem == null) {
            viewHolder.root.performClick()
        }
    }

    fun setData(it: List<PaymentPlan>) {
        plans = it
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: ConstraintLayout = itemView.findViewById(R.id.cl_plan_root)
        val amount: TextView = itemView.findViewById(R.id.tv_plan_amount) as TextView
        val duration: TextView = itemView.findViewById(R.id.tv_plan_duration) as TextView
    }

}
