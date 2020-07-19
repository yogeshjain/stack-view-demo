package com.example.stackviewdemo.view

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.example.stackviewdemo.R
import com.example.stackviewdemo.viewmodel.BaseStackViewModel
import com.example.stackviewdemo.viewmodel.ViewAction
import kotlinx.android.synthetic.main.activity_stack_demo.*

/**
 * Extend this activity and BaseStackViewModel to implement a stacked activity
 * This activity will handle stack transactions and handle back press. Order of input and type of stack components can be controlled by viewModel
 */
abstract class BaseStackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stack_demo)

        ll_stack_root.removeAllViews()

        getViewModel().getViewActions().observe(this, Observer {
            when(it.first) {
                ViewAction.ADD -> {
                    addView(it.second)
                }
                ViewAction.REMOVE -> {
                    ll_stack_root.removeViewAt(ll_stack_root.childCount - 1)
                }
                ViewAction.REPLACE -> {
                    ll_stack_root.removeViewAt(ll_stack_root.childCount - 1)
                    addView(it.second)
                }
                ViewAction.COLLAPSE_TOP -> {
                    collapse(ll_stack_root.getChildAt(ll_stack_root.childCount - 1))
                }
                ViewAction.EXPAND_TOP -> {
                    expand(ll_stack_root.getChildAt(ll_stack_root.childCount - 1))
                }
            }
            btn_main.text = getViewModel().getBottomButtonText()
        })

        btn_main.setOnClickListener { getViewModel().onButtonClick() }
        iv_close.setOnClickListener { finish() }
        getViewModel().initialize()
    }

    private fun collapse(view: View?) {
        view?.let {
            it.findViewById<ImageView>(R.id.iv_expand).visibility = View.VISIBLE
            val pos = ll_stack_root.childCount - 1
            it.setOnClickListener {
                getViewModel().expand(pos)
            }
        }
    }

    private fun expand(view: View?) {
        view?.let {
            it.findViewById<ImageView>(R.id.iv_expand).visibility = View.GONE
            it.setOnClickListener {
            }
        }
    }

    private fun addView(frag: Fragment?) {
        if(frag != null) {
            val frame = layoutInflater.inflate(R.layout.fragment_holder, ll_stack_root, false)
            val id = ViewCompat.generateViewId()
            frame.findViewById<FrameLayout>(R.id.fr_holder).id = id
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(id, frag)
            ft.commit()
            ll_stack_root.addView(frame)
        }
    }

    override fun onBackPressed() {
        if(!getViewModel().onBackButtonPress()) {
            super.onBackPressed()
        }
    }

    protected fun getRootView(): View = ll_stack_root

    abstract fun getViewModel(): BaseStackViewModel

}