package com.example.stackviewdemo.model

import com.example.stackviewdemo.view.abstract.StackedView

/**
 * Data structure to hold stack of Stacked View type
 * This can be used to manage view stack for implementation of stacked view
 */
class ViewStack {
    private val stack = mutableListOf<StackedView>()
    
    fun push(view: StackedView) = stack.add(view)

    fun pop(): StackedView = stack.removeAt(stack.size -1)

    fun peek(): StackedView = stack.last()

    fun isEmpty() = stack.isEmpty()
    fun size(): Int = stack.size
}