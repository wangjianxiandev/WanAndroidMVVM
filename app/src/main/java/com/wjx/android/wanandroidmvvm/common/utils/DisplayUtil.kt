package com.wjx.android.wanandroidmvvm.common.utils

import android.content.Context

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
}