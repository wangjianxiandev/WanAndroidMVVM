package com.wjx.android.wanandroidmvvm.Custom.behavior

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wjx.android.wanandroidmvvm.Custom.ScrollAwareFab

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/26
 * Time: 21:34
 */
class ScrollAwareFabBehavior(context: Context, attributeSet: AttributeSet) :
    ScrollAwareFab(context, attributeSet) {
    private var mIsAnimatingOut = false

    override fun onScrollDown(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnConsumed: Int
    ) {
        super.onScrollDown(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnConsumed
        )
        if (!mIsAnimatingOut && child.visibility == View.VISIBLE) {
            animateOut(child)
        }
    }

    override fun onScrollUp(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnConsumed: Int
    ) {
        super.onScrollUp(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnConsumed
        )
        if (child.visibility == View.INVISIBLE) {
            animateIn(child)
        }
    }

    private fun animateOut(button: FloatingActionButton) {
        if (Build.VERSION.SDK_INT >= 22) {
            ViewCompat.animate(button).translationX((button.width + getMarginBottom(button)).toFloat())
                .setInterpolator(INTERPOLATOR).withLayer()
                .setListener(object : ViewPropertyAnimatorListener{
                    override fun onAnimationStart(view: View) {
                        this@ScrollAwareFabBehavior.mIsAnimatingOut = true
                    }

                    override fun onAnimationCancel(view: View) {
                        this@ScrollAwareFabBehavior.mIsAnimatingOut = false
                    }

                    override fun onAnimationEnd(view: View) {
                        this@ScrollAwareFabBehavior.mIsAnimatingOut = false
                        view.visibility = View.INVISIBLE
                    }
                }).start()
        } else {
            button.hide()
        }
    }


    private fun animateIn(button: FloatingActionButton) {
        button.show()
        if (Build.VERSION.SDK_INT >= 22){
            ViewCompat.animate(button).translationX(0f)
                .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                .start()
        } else {
            button.show()
        }
    }

    private fun getMarginBottom(v : View) : Int {
        var marginBottom = 0
        val layoutParams = v.layoutParams
        if (layoutParams is ViewGroup.MarginLayoutParams) {
            marginBottom = layoutParams.bottomMargin
        }
        return marginBottom
    }

    companion object {
        private val INTERPOLATOR = LinearInterpolator()
    }
}