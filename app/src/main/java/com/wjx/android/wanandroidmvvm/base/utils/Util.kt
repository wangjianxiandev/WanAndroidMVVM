package com.wjx.android.wanandroidmvvm.base.utils

import android.content.Context
import android.graphics.Color
import java.lang.reflect.ParameterizedType
import java.util.*


/**
 * author：  HyZhan
 * created： 2018/10/11 16:04
 * desc：    工具类
 */
object Util {
    fun <T> getClass(t: Any): Class<T> {
        // 通过反射 获取父类泛型 (T) 对应 Class类
        return (t.javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[0]
                as Class<T>
    }

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

    fun px2sp(context: Context, pxValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f)
    }
}