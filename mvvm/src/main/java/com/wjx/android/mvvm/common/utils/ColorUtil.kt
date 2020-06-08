package com.wjx.android.mvvm.common.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.wjx.android.mvvm.R
import java.util.*
import kotlin.jvm.internal.Intrinsics

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/27
 * Time: 18:21
 */
object ColorUtil {
    //自定义颜色，过滤掉与白色相近的颜色
    var ACCENT_COLORS = intArrayOf(
        Color.parseColor("#EF5350"),
        Color.parseColor("#EC407A"),
        Color.parseColor("#AB47BC"),
        Color.parseColor("#7E57C2"),
        Color.parseColor("#5C6BC0"),
        Color.parseColor("#42A5F5"),
        Color.parseColor("#29B6F6"),
        Color.parseColor("#26C6DA"),
        Color.parseColor("#26A69A"),
        Color.parseColor("#66BB6A"),
        Color.parseColor("#9CCC65"),
        Color.parseColor("#D4E157"),
        Color.parseColor("#FFEE58"),
        Color.parseColor("#FFCA28"),
        Color.parseColor("#FFA726"),
        Color.parseColor("#FF7043"),
        Color.parseColor("#8D6E63"),
        Color.parseColor("#BDBDBD"),
        Color.parseColor("#78909C")
    )

    val PRIMARY_COLORS_SUB = arrayOf(
        intArrayOf(
            Color.parseColor("#EF5350"),
            Color.parseColor("#F44336"),
            Color.parseColor("#E53935"),
            Color.parseColor("#D32F2F"),
            Color.parseColor("#C62828"),
            Color.parseColor("#B71C1C")
        ),
        intArrayOf(
            Color.parseColor("#EC407A"),
            Color.parseColor("#E91E63"),
            Color.parseColor("#D81B60"),
            Color.parseColor("#C2185B"),
            Color.parseColor("#AD1457"),
            Color.parseColor("#880E4F")
        ),
        intArrayOf(
            Color.parseColor("#AB47BC"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#8E24AA"),
            Color.parseColor("#7B1FA2"),
            Color.parseColor("#6A1B9A"),
            Color.parseColor("#4A148C")
        ),
        intArrayOf(
            Color.parseColor("#7E57C2"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#5E35B1"),
            Color.parseColor("#512DA8"),
            Color.parseColor("#4527A0"),
            Color.parseColor("#311B92")
        ),
        intArrayOf(
            Color.parseColor("#5C6BC0"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#3949AB"),
            Color.parseColor("#303F9F"),
            Color.parseColor("#283593"),
            Color.parseColor("#1A237E")
        ),
        intArrayOf(
            Color.parseColor("#42A5F5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#1E88E5"),
            Color.parseColor("#1976D2"),
            Color.parseColor("#1565C0"),
            Color.parseColor("#0D47A1")
        ),
        intArrayOf(
            Color.parseColor("#29B6F6"),
            Color.parseColor("#03A9F4"),
            Color.parseColor("#039BE5"),
            Color.parseColor("#0288D1"),
            Color.parseColor("#0277BD"),
            Color.parseColor("#01579B")
        ),
        intArrayOf(
            Color.parseColor("#26C6DA"),
            Color.parseColor("#00BCD4"),
            Color.parseColor("#00ACC1"),
            Color.parseColor("#0097A7"),
            Color.parseColor("#00838F"),
            Color.parseColor("#006064")
        ),
        intArrayOf(
            Color.parseColor("#26A69A"),
            Color.parseColor("#009688"),
            Color.parseColor("#00897B"),
            Color.parseColor("#00796B"),
            Color.parseColor("#00695C"),
            Color.parseColor("#004D40")
        ),
        intArrayOf(
            Color.parseColor("#66BB6A"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#43A047"),
            Color.parseColor("#388E3C"),
            Color.parseColor("#2E7D32"),
            Color.parseColor("#1B5E20")
        ),
        intArrayOf(
            Color.parseColor("#9CCC65"),
            Color.parseColor("#8BC34A"),
            Color.parseColor("#7CB342"),
            Color.parseColor("#689F38"),
            Color.parseColor("#558B2F"),
            Color.parseColor("#33691E")
        ),
        intArrayOf(
            Color.parseColor("#D4E157"),
            Color.parseColor("#CDDC39"),
            Color.parseColor("#C0CA33"),
            Color.parseColor("#AFB42B"),
            Color.parseColor("#9E9D24"),
            Color.parseColor("#827717")
        ),
        intArrayOf(
            Color.parseColor("#FFEE58"),
            Color.parseColor("#FFEB3B"),
            Color.parseColor("#FDD835"),
            Color.parseColor("#FBC02D"),
            Color.parseColor("#F9A825"),
            Color.parseColor("#F57F17")
        ),
        intArrayOf(
            Color.parseColor("#FFCA28"),
            Color.parseColor("#FFC107"),
            Color.parseColor("#FFB300"),
            Color.parseColor("#FFA000"),
            Color.parseColor("#FF8F00"),
            Color.parseColor("#FF6F00")
        ),
        intArrayOf(
            Color.parseColor("#FFA726"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FB8C00"),
            Color.parseColor("#F57C00"),
            Color.parseColor("#EF6C00"),
            Color.parseColor("#E65100")
        ),
        intArrayOf(
            Color.parseColor("#FF7043"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#F4511E"),
            Color.parseColor("#E64A19"),
            Color.parseColor("#D84315"),
            Color.parseColor("#BF360C")
        ),
        intArrayOf(
            Color.parseColor("#8D6E63"),
            Color.parseColor("#795548"),
            Color.parseColor("#6D4C41"),
            Color.parseColor("#5D4037"),
            Color.parseColor("#4E342E"),
            Color.parseColor("#3E2723")
        ),
        intArrayOf(
            Color.parseColor("#BDBDBD"),
            Color.parseColor("#9E9E9E"),
            Color.parseColor("#757575"),
            Color.parseColor("#616161"),
            Color.parseColor("#424242"),
            Color.parseColor("#212121")
        ),
        intArrayOf(
            Color.parseColor("#78909C"),
            Color.parseColor("#607D8B"),
            Color.parseColor("#546E7A"),
            Color.parseColor("#455A64"),
            Color.parseColor("#37474F"),
            Color.parseColor("#263238")
        )
    )

    /**
     * 获取随机rgb颜色值
     */
    fun randomColor(): Int {
        val random = Random()
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        val red = random.nextInt(150)
        //0-190
        val green = random.nextInt(150)
        //0-190
        val blue = random.nextInt(150)
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue)
    }

    /**
     * 从右下到左上计算渐变颜色值 ARGB
     *
     * @param fraction
     * 变化比率 0~1
     * @param startValue
     * 初始色值
     * @param endValue
     * 结束色值
     * @return
     */
    fun calculateGradient(fraction: Float, startValue: Any, endValue: Int): Int {
        val startInt = startValue as Int
        val startA = startInt shr 24 and 0xff
        val startR = startInt shr 16 and 0xff
        val startG = startInt shr 8 and 0xff
        val startB = startInt and 0xff
        val endInt = endValue
        val endA = endInt shr 24 and 0xff
        val endR = endInt shr 16 and 0xff
        val endG = endInt shr 8 and 0xff
        val endB = endInt and 0xff
        return ((startA + (fraction * (endA - startA)).toInt() shl 24)
                or (startR + (fraction * (endR - startR)).toInt() shl 16)
                or (startG + (fraction * (endG - startG)).toInt() shl 8)
                or (startB + (fraction * (endB - startB)).toInt()))
    }

    /**
     * 获取主题颜色
     *
     * @param context
     * @return
     */
    fun getColor(context: Context): Int {
        val defaultColor = 0
        var colorTheme: Int by SPreference("color", defaultColor)
        return if (colorTheme != 0 && Color.alpha(colorTheme) != 255) {
            defaultColor
        } else {
            colorTheme
        }
    }

    /**
     * 设置主题颜色
     *
     * @param context
     * @param color
     */
    fun setColor(color: Int) {
        var colorTheme: Int by SPreference("color", color)
        colorTheme = color
    }

    /**
     * BottomNavigation 适配颜色
     *
     * @param context
     * @return
     */
    fun getColorStateList(context: Context): ColorStateList {
        Intrinsics.checkParameterIsNotNull(context, "context")
        val colors = intArrayOf(
            getColor(context)
        )
        val states = arrayOf(
            intArrayOf(
                android.R.attr.state_checked,
                android.R.attr.state_checked
            ), IntArray(0)
        )
        return ColorStateList(states, colors)
    }

    /**
     * Float button 适配颜色
     *
     * @param context
     * @return
     */
    fun getOneColorStateList(context: Context): ColorStateList {
        Intrinsics.checkParameterIsNotNull(context, "context")
        val colors =
            intArrayOf(getColor(context))
        val states = arrayOf(IntArray(0))
        return ColorStateList(states, colors)
    }

    /**
     * 设置切换夜间模式之前的主题颜色
     * @param color
     */
    fun setLastColor(color: Int) {
        var lastColor: Int by SPreference("lastColor", color)
        lastColor = color
    }

    /**
     * 获取切换夜间模式之前的主题色
     *
     * @param context
     * @return
     */
    fun getLastColor(context: Context): Int {
        val defaultColor = 0
        var lastColor: Int by SPreference("lastColor", defaultColor)
        return if (lastColor != 0 && Color.alpha(lastColor) != 255) {
            defaultColor
        } else {
            lastColor
        }
    }
}