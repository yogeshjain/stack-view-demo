package com.example.stackviewdemo.view

import android.view.View
import com.example.stackviewdemo.view.abstract.StackedView

class MockStackView(val tag: Int): StackedView() {
    override fun getBottomActionText(): String {
        TODO("Not yet implemented")
    }

    override fun onBottomItemClick() {
        TODO("Not yet implemented")
    }

    override fun getCollapsedView(): View {
        TODO("Not yet implemented")
    }

    override fun getExpandedView(): View {
        TODO("Not yet implemented")
    }

    fun getMockTag() = tag
}