package com.example.stackviewdemo.stack.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackviewdemo.R
import com.example.stackviewdemo.stack.viewModel.RadioSIViewModel
import com.example.stackviewdemo.view.abstract.StackedView
import com.example.stackviewdemo.view.adapter.RadioAdapter
import kotlinx.android.synthetic.main.si_radio.*

class RadioSItem() : StackedView() {
    val radioViewModel: RadioSIViewModel by viewModels()
    lateinit var items: List<Pair<String, String?>>
    lateinit var title: String
    lateinit var subtitle: String
    lateinit var buttonText: String

    var adapter: RadioAdapter? = null

    private constructor(items: List<Pair<String, String?>>, title: String, subtitle: String,
                        buttonText: String) : this() {
        this.items = items
        this.title = title
        this.subtitle = subtitle
        this.buttonText = buttonText
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        isExpanded = true
        return inflater.inflate(R.layout.si_radio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_radio_row_title.text = title
        tv_radio_subtitle.text = subtitle

        if(adapter == null) {
            adapter = RadioAdapter(items) {
                radioViewModel.selectedItem = it
                tv_radio_selected.text = "${it.first} " + if(it.second != null) "(${it.second})" else ""
            }
            rv_radio.layoutManager = LinearLayoutManager(activity)
            rv_radio.adapter = adapter
        }
    }

    override fun getBottomActionText() = buttonText

    override fun onBottomItemClick() {
        //Do nothing
    }

    override fun getCollapsedView(): View {
        return tv_radio_selected
    }

    override fun getExpandedView(): View {
        return ll_radio_expanded
    }

    fun getSelectedItem() = radioViewModel.selectedItem

    companion object{
        fun getInstance(items: List<Pair<String, String?>>, title: String, subtitle: String,
                        buttonText: String
        ): RadioSItem {
            return RadioSItem(items, title, subtitle, buttonText)
        }
    }

}