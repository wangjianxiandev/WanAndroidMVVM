package com.wjx.android.wanandroidmvvm.Custom.graylayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout


/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/5 14:01
 */
class GrayFrameLayout(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs) {
    private val mPaint: Paint = Paint()
    override fun dispatchDraw(canvas: Canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.dispatchDraw(canvas)
        canvas.restore()
    }

    override fun draw(canvas: Canvas) {
        canvas.saveLayer(null, mPaint, Canvas.ALL_SAVE_FLAG)
        super.draw(canvas)
        canvas.restore()
    }

    init {
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        mPaint.setColorFilter(ColorMatrixColorFilter(cm))
    }
}
