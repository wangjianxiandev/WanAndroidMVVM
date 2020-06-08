package com.wjx.android.mvvm.common.utils

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 22:05
 */
class SpeedLayoutManager(val context: Context?, val speed: Float = 25f) : LinearLayoutManager(context) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                // 返回：滑过1px时经历的时间(ms)。
                return speed / (displayMetrics?.densityDpi ?: 1)
            }
        }

        // 设置要 移动的位置
        smoothScroller.targetPosition = position
        // 开始移动
        startSmoothScroll(smoothScroller)
    }
}