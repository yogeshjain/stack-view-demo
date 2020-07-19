package com.example.stackviewdemo.viewmodel

import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stackviewdemo.model.PaymentPlan
import com.example.stackviewdemo.model.ViewStack
import com.example.stackviewdemo.stack.view.AmountSItem
import com.example.stackviewdemo.stack.view.EmiSItem
import com.example.stackviewdemo.stack.view.RadioSItem
import com.example.stackviewdemo.view.abstract.StackedView

/**
 * Implement this and BAseStackedActivity to implement a stacked view
 * This takes care of stack persistence and order.
 */
abstract class BaseStackViewModel : ViewModel() {
    private val viewStack: ViewStack = ViewStack()
    private val viewAction: MutableLiveData<Pair<ViewAction, StackedView?>> = MutableLiveData()
    fun getViewActions(): LiveData<Pair<ViewAction, StackedView?>> = viewAction

    fun initialize() {
        if (viewStack.isEmpty()) {
            getItemAtPos(0)?.let { addView(it) }
        }
    }

    fun addView(stackItem: StackedView) {
        viewStack.push(stackItem)
        viewAction.postValue(Pair(ViewAction.ADD, viewStack.peek()))
    }

    fun getBottomButtonText(): String? = viewStack.peek().getBottomActionText()

    fun onButtonClick() {
        if(getValueAndValidate(viewStack.peek(), viewStack.size())) {
            viewStack.peek().onBottomItemClick()
            val nextView = getItemAtPos(viewStack.size())
            if(nextView != null) {
                viewStack.peek().collapse()
                viewAction.value = (Pair(ViewAction.COLLAPSE_TOP, null))
                addView(nextView)
            }
        }
    }

    /**
     * Return StackView to be inserted at this position
     */
    abstract fun getItemAtPos(pos: Int): StackedView?

    /**
     * Validate and process value at given stage
     */
    abstract fun getValueAndValidate(stackedView: StackedView, pos: Int): Boolean

    fun expand(position: Int) {
        if (position < viewStack.size()) {
            while (viewStack.size() > position + 1) {
                viewStack.pop()
                viewAction.value = (Pair(ViewAction.REMOVE, null))
            }
            viewStack.peek().expand()
            viewAction.postValue(Pair(ViewAction.EXPAND_TOP, null))
        }
    }

    fun onBackButtonPress(): Boolean {
        return if(viewStack.size() > 1) {
            viewStack.pop()
            viewAction.value = (Pair(ViewAction.REMOVE, null))
            viewStack.peek().expand()
            viewAction.value = Pair(ViewAction.EXPAND_TOP, null)
            true
        } else
            false
    }
}


enum class ViewAction {
    ADD, REMOVE, REPLACE, COLLAPSE_TOP, EXPAND_TOP
}