package com.wjx.android.wanandroidmvvm.Custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * Created with Android Studio.
 * Description: 含有用户默认昵称的头像
 *
 * @author: Wangjianxian
 * @date: 2020/02/07
 * Time: 15:07
 */
@SuppressLint("AppCompatCustomView")
class CustomUserAvatar : ImageView {
    private var mPaintText: Paint? = null
    private var mPaintBackground: Paint? = null
    private var mRect: Rect? = null
    private var mCircleName: String? = null
    private var mColor = 0
    private var mCount = 0
    private var mIsShowBlurMask: Boolean = false

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mPaintText = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintBackground = Paint(Paint.ANTI_ALIAS_FLAG)
        mRect = Rect()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制发光效果
        mPaintBackground!!.color = mColor
        mPaintBackground!!.style = Paint.Style.STROKE
        mPaintBackground!!.strokeWidth = 5f
        if (mIsShowBlurMask) {
            mPaintBackground!!.maskFilter = BlurMaskFilter(10.0f, BlurMaskFilter.Blur.SOLID)
        }
        canvas.drawCircle(
            width / 2.toFloat(),
            width / 2.toFloat(),
            (width - 20) / 2.toFloat(),
            mPaintBackground!!
        )
        // 设置文本大小
        mPaintText!!.textSize = width / 3.toFloat()
        // 设置文本颜色跟随应用主题颜色
        mPaintText!!.color = mColor
        // 设置画笔粗细
        mPaintText!!.strokeWidth = 5f
        // 设置阴影半径
        mPaintText!!.setShadowLayer(5f, 5f, 5f, Color.BLACK)
        // 绘制文字的最小矩形
        mPaintText!!.getTextBounds(mCircleName, 0, 1, mRect)
        val fontMetricsInt = mPaintText!!.fontMetricsInt
        // baseLine上面是负值，下面是正值
        // 所以getHeight()/2-fontMetricsInt.descent 将文本的bottom线抬高至控件的1/2处
        // + (fontMetricsInt.bottom - fontMetricsInt.top) / 2：(fontMetricsInt.bottom - fontMetricsInt.top) 文本的辅助线（top+bottom)/2就是文本的中位线（我是这样理解的）恰好在控件中位线处
        val baseLine =
            height / 2 - fontMetricsInt.descent + (fontMetricsInt.bottom - fontMetricsInt.top) / 2
        // 水平居中
        mPaintText!!.textAlign = Paint.Align.CENTER
        canvas.drawText(mCircleName!!, width / 2.toFloat(), baseLine.toFloat(), mPaintText!!)
    }

    /**
     * 判断一个字符是否是中文
     */
    fun isChineseChar(c: Char): Boolean { // 根据字节码判断
        return c.toInt() >= 0x4E00 && c.toInt() <= 0x9FA5
    }

    /**
     * 判断一个字符串是否含有中文
     *
     * @param str
     * @return
     */
    fun isChineseString(str: String?): Boolean {
        if (str == null) {
            return false
        }
        for (c in str.toCharArray()) {
            if (isChineseChar(c)) {
                return true
            }
        }
        return false
    }

    /**
     * 设置显示的名字
     *
     * @param circleName
     */
    fun setCircleName(circleName: String) { // 中文名字取后两个
        if (isChineseString(circleName)) {
            mCircleName = if (circleName.length > if(mCount == 0)  2 else mCount) {
                circleName.substring(0, if(mCount == 0)  2 else mCount)
            } else {
                circleName
            }
        } else { // 非中文名字取第一个
            if (circleName.length > 1) {
                mCircleName = circleName.substring(0, 1)
                mCircleName = mCircleName!!.toUpperCase()
            } else {
                mCircleName = circleName
                mCircleName = mCircleName!!.toUpperCase()
            }
        }
        invalidate()
    }

    fun setIsShowBlurMask(isShow: Boolean) {
        mIsShowBlurMask = isShow
    }

    fun setShowNameCount(count: Int) {
        mCount = count
    }

    fun setColor(color: Int) {
        mColor = color
    }
}