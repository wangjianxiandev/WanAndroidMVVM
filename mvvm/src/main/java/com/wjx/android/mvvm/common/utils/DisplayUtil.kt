package com.wjx.android.mvvm.common.utils

import android.animation.ValueAnimator
import android.content.Context
import android.view.animation.DecelerateInterpolator
import android.widget.TextView

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/5 20:26
 */
object DisplayUtil{
    /**
     * dp与px转换
     *
     * @param context
     * @param dp
     * @return
     */
    fun dp2Px(context: Context, dp: Int): Int {
        val density: Float
        density = context.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    fun startIntegralTextAnim(textView : TextView, value: Int, textType : String) {
        val animator = ValueAnimator.ofInt(0, value)
        //播放时长
        animator.duration = 1500
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { animation ->
            //获取改变后的值
            val currentValue = animation.animatedValue as Int
            textView.text = "$textType $currentValue"
        }
        animator.start()
    }
}