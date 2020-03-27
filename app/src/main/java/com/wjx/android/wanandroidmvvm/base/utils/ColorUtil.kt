package com.wjx.android.wanandroidmvvm.base.utils

import android.graphics.Color
import java.util.*

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
        Color.parseColor("#78909C"))

    val PRIMARY_COLORS_SUB = arrayOf(
        intArrayOf(Color.parseColor("#EF5350"), Color.parseColor("#F44336"), Color.parseColor("#E53935"), Color.parseColor("#D32F2F"), Color.parseColor("#C62828"), Color.parseColor("#B71C1C")),
        intArrayOf(Color.parseColor("#EC407A"), Color.parseColor("#E91E63"), Color.parseColor("#D81B60"), Color.parseColor("#C2185B"), Color.parseColor("#AD1457"), Color.parseColor("#880E4F")),
        intArrayOf(Color.parseColor("#AB47BC"), Color.parseColor("#9C27B0"), Color.parseColor("#8E24AA"), Color.parseColor("#7B1FA2"), Color.parseColor("#6A1B9A"), Color.parseColor("#4A148C")),
        intArrayOf(Color.parseColor("#7E57C2"), Color.parseColor("#673AB7"), Color.parseColor("#5E35B1"), Color.parseColor("#512DA8"), Color.parseColor("#4527A0"), Color.parseColor("#311B92")),
        intArrayOf(Color.parseColor("#5C6BC0"), Color.parseColor("#3F51B5"), Color.parseColor("#3949AB"), Color.parseColor("#303F9F"), Color.parseColor("#283593"), Color.parseColor("#1A237E")),
        intArrayOf(Color.parseColor("#42A5F5"), Color.parseColor("#2196F3"), Color.parseColor("#1E88E5"), Color.parseColor("#1976D2"), Color.parseColor("#1565C0"), Color.parseColor("#0D47A1")),
        intArrayOf(Color.parseColor("#29B6F6"), Color.parseColor("#03A9F4"), Color.parseColor("#039BE5"), Color.parseColor("#0288D1"), Color.parseColor("#0277BD"), Color.parseColor("#01579B")),
        intArrayOf(Color.parseColor("#26C6DA"), Color.parseColor("#00BCD4"), Color.parseColor("#00ACC1"), Color.parseColor("#0097A7"), Color.parseColor("#00838F"), Color.parseColor("#006064")),
        intArrayOf(Color.parseColor("#26A69A"), Color.parseColor("#009688"), Color.parseColor("#00897B"), Color.parseColor("#00796B"), Color.parseColor("#00695C"), Color.parseColor("#004D40")),
        intArrayOf(Color.parseColor("#66BB6A"), Color.parseColor("#4CAF50"), Color.parseColor("#43A047"), Color.parseColor("#388E3C"), Color.parseColor("#2E7D32"), Color.parseColor("#1B5E20")),
        intArrayOf(Color.parseColor("#9CCC65"), Color.parseColor("#8BC34A"), Color.parseColor("#7CB342"), Color.parseColor("#689F38"), Color.parseColor("#558B2F"), Color.parseColor("#33691E")),
        intArrayOf(Color.parseColor("#D4E157"), Color.parseColor("#CDDC39"), Color.parseColor("#C0CA33"), Color.parseColor("#AFB42B"), Color.parseColor("#9E9D24"), Color.parseColor("#827717")),
        intArrayOf(Color.parseColor("#FFEE58"), Color.parseColor("#FFEB3B"), Color.parseColor("#FDD835"), Color.parseColor("#FBC02D"), Color.parseColor("#F9A825"), Color.parseColor("#F57F17")),
        intArrayOf(Color.parseColor("#FFCA28"), Color.parseColor("#FFC107"), Color.parseColor("#FFB300"), Color.parseColor("#FFA000"), Color.parseColor("#FF8F00"), Color.parseColor("#FF6F00")),
        intArrayOf(Color.parseColor("#FFA726"), Color.parseColor("#FF9800"), Color.parseColor("#FB8C00"), Color.parseColor("#F57C00"), Color.parseColor("#EF6C00"), Color.parseColor("#E65100")),
        intArrayOf(Color.parseColor("#FF7043"), Color.parseColor("#FF5722"), Color.parseColor("#F4511E"), Color.parseColor("#E64A19"), Color.parseColor("#D84315"), Color.parseColor("#BF360C")),
        intArrayOf(Color.parseColor("#8D6E63"), Color.parseColor("#795548"), Color.parseColor("#6D4C41"), Color.parseColor("#5D4037"), Color.parseColor("#4E342E"), Color.parseColor("#3E2723")),
        intArrayOf(Color.parseColor("#BDBDBD"), Color.parseColor("#9E9E9E"), Color.parseColor("#757575"), Color.parseColor("#616161"), Color.parseColor("#424242"), Color.parseColor("#212121")),
        intArrayOf(Color.parseColor("#78909C"), Color.parseColor("#607D8B"), Color.parseColor("#546E7A"), Color.parseColor("#455A64"), Color.parseColor("#37474F"), Color.parseColor("#263238")))

    /**
     * 获取随机rgb颜色值
     */
    fun randomColor(): Int {
        Random().run {
            //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
            val red = nextInt(190)
            val green = nextInt(190)
            val blue = nextInt(190)
            //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
            return Color.rgb(red, green, blue)
        }
    }
}