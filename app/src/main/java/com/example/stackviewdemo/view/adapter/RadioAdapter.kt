package com.example.stackviewdemo.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.stackviewdemo.R

class RadioAdapter(
    itemList: List<Pair<String, String?>>?,
    private val onSelect: (pair: Pair<String, String?>) -> Unit
) : RecyclerView.Adapter<RadioAdapter.ViewHolder>() {
    var items = itemList
    var selectedItem: RadioButton? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): ViewHolder {
        val rootView =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.row_radio, viewGroup, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return items?.size!!
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, index: Int) {
        val pair = items?.get(index)
        viewHolder.title.text = pair?.first
        if (pair?.second != null) {
            viewHolder.subtitle.visibility = View.VISIBLE
            viewHolder.subtitle.text = pair.second
        } else {
            viewHolder.subtitle.visibility = View.GONE
        }

        pair?.let {
            viewHolder.root.setOnClickListener {
                selectedItem?.isChecked = false
                viewHolder.radio.isChecked = true
                selectedItem = viewHolder.radio
                onSelect(pair)
            }
            viewHolder.radio.setOnClickListener {
                selectedItem?.isChecked = false
                viewHolder.radio.isChecked = true
                selectedItem = viewHolder.radio
                onSelect(pair)
            }
        }

        if (selectedItem == null) {
            viewHolder.root.performClick()
        }
    }

    fun setData(it: List<Pair<String, String?>>) {
        items = it
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radio: RadioButton = itemView.findViewById(R.id.rb_radio_list)
        val title: TextView = itemView.findViewById(R.id.tv_radio_row_title) as TextView
        val subtitle: TextView = itemView.findViewById(R.id.tv_radio_row_subtext) as TextView
        val root: ConstraintLayout = itemView.findViewById(R.id.cl_radio_root) as ConstraintLayout
    }

}
