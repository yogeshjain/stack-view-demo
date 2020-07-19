package com.example.stackviewdemo.view.abstract

import android.animation.Animator
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment


abstract class StackedView : Fragment() {
    var isExpanded = true
    /**
     * Return bottom view label
     */
    abstract fun getBottomActionText(): String

    /**
     * Notify the stack item of actionable click
     * Any stack specific data/state changes on positive action should be performed here
     */
    abstract fun onBottomItemClick()

    abstract fun getCollapsedView(): View

    abstract fun getExpandedView(): View

    /**
     * Set current view as collapsed
     */
    fun collapse() {
        isExpanded = false
        getCollapsedView().visibility = View.VISIBLE
        collapseView(getExpandedView())
        hide(getExpandedView())
    }

    /**
     * Set current view as expanded
     */
    fun expand() {
        isExpanded = true
        getExpandedView().layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        getExpandedView().requestLayout()
        show(getExpandedView())
        getCollapsedView().visibility = View.GONE
    }


    //View animations
    private fun hide(view: View) {
        view.alpha = 1.0f;
        view.visibility = View.VISIBLE;
        view.animate()
            .alpha(0f)
            .setDuration(300)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    view.visibility = View.GONE
                }
            })
    }

    private fun show(view: View) {
        view.alpha = 0f;
        view.visibility = View.VISIBLE;
        view.animate()
            .alpha(1.0f)
            .setDuration(300)
            .setListener(null)
    }

    private fun collapseView(v: View) {
        val initialHeight = v.measuredHeight
        val a: Animation = object : Animation() {
            override fun applyTransformation(
                interpolatedTime: Float,
                t: Transformation?
            ) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = (initialHeight / v.context.resources
            .displayMetrics.density).toLong()
        v.startAnimation(a)
    }
}