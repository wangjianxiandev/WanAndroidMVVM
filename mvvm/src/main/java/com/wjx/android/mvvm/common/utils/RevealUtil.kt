package com.wjx.android.wanandroidmvvm.common.utils

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.google.android.material.circularreveal.CircularRevealCompat
import com.google.android.material.circularreveal.CircularRevealFrameLayout
import com.google.android.material.circularreveal.CircularRevealWidget
import kotlin.math.hypot

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/5 20:25
 */
object RevealUtil{
    fun Activity.setReveal(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val container = window.decorView as FrameLayout
            val rootLayout = container.getChildAt(0)
            container.removeView(rootLayout)
            val layout = CircularRevealFrameLayout(this)
            layout.addView(rootLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
            container.addView(layout)
            container.onPreDrawLayout {
                circularReveal(layout)
            }
        }
    }

    inline fun <T : View> T.onPreDrawLayout(crossinline  onLayout: T.()->Unit){
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {

                override fun onPreDraw(): Boolean {
                    onLayout()
                    viewTreeObserver.removeOnPreDrawListener(this)
                    return false
                }
            })
        }
    }

    fun circularReveal(rootLayout : CircularRevealFrameLayout) {
        val centerX = rootLayout.width.toFloat() / 2
        val centerY = rootLayout.height.toFloat() / 2
        val finalRadius = hypot(
            centerX.coerceAtLeast(rootLayout.width  - centerX),
            centerY.coerceAtLeast(rootLayout.height - centerY)
        )

        rootLayout.revealInfo = CircularRevealWidget.RevealInfo(centerX, centerY, 0f)
        val circularReveal = CircularRevealCompat.createCircularReveal(rootLayout, centerX, centerY, finalRadius)
        circularReveal.duration = 500
        circularReveal.interpolator = LinearInterpolator()
        circularReveal.start()
    }

    fun circularFinishReveal(rootLayout: View) {
        val centerX = rootLayout.width.toFloat() /2
        val centerY = rootLayout.height.toFloat() /2
        val finalRadius = hypot(
            centerX.coerceAtLeast(rootLayout.width - centerX),
            centerY.coerceAtLeast(rootLayout.height - centerY)
        )
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            rootLayout,
            (rootLayout.width * 0.5).toInt(),
            (rootLayout.height * 0.5).toInt(), finalRadius,0f
        )
        circularReveal.duration = 500
        circularReveal.interpolator = LinearInterpolator()
//        给背景增加0.8f的alpha值
//        rootLayout.postDelayed({
//            rootLayout.alpha = 0.8f
//        },250)
        circularReveal.start()
    }
}