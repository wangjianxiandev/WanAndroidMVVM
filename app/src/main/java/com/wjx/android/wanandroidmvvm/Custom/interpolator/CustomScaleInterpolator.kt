package com.wjx.android.wanandroidmvvm.Custom.interpolator

import android.view.animation.Interpolator

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 20:16
 */

class CustomScaleInterpolator(elasticFactor: Float) :
    Interpolator {

    private val elasticFactor: Float

    override fun getInterpolation(input: Float): Float {
        return (Math.pow(
            2.0,
            -10 * input.toDouble()
        ) * Math.sin((input - elasticFactor / 4) * (2 * Math.PI) / elasticFactor) + 1).toFloat()
    }

    init {
        this.elasticFactor = elasticFactor
    }
}