package com.wjx.android.wanandroidmvvm.Custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created with Android Studio.
 * Description: 自定义随着滑动消失的fab
 * @author: Wangjianxian
 * @date: 2020/02/26
 * Time: 21:35
 */
open class ScrollAwareFab(context: Context, attributeSet: AttributeSet) : FloatingActionButton.Behavior() {
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
        if (dyConsumed > 0) {
            onScrollDown(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        } else if (dyConsumed < 0 ) {
            onScrollUp(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        }
    }

    open fun onScrollDown(coordinatorLayout: CoordinatorLayout,
                          child: FloatingActionButton,
                          target: View,
                          dxConsumed: Int,
                          dyConsumed: Int,
                          dxUnconsumed: Int,
                          dyUnConsumed: Int) {
    }

    open fun onScrollUp(coordinatorLayout: CoordinatorLayout,
                        child: FloatingActionButton,
                        target: View,
                        dxConsumed: Int,
                        dyConsumed: Int,
                        dxUnconsumed: Int,
                        dyUnConsumed: Int) {
    }
}