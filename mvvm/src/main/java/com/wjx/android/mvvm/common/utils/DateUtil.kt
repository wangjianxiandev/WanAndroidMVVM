package com.wjx.android.mvvm.common.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.internal.Intrinsics

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/4/5 20:24
 */
object DateUtil {
    /**
     * 获取当前时刻
     *
     * @return
     */
    fun getNowTime(): Date? {
        return formatDate("yyyy-MM-dd", Date(Date().time))
    }

    /**
     * 日期格式化
     *
     * @param formatStyle
     * @param date
     * @return
     */
    fun formatDate(formatStyle: String, date: Date?): Date? {
        Intrinsics.checkParameterIsNotNull(formatStyle, "formatStyle")
        return if (date != null) {
            val sdf = SimpleDateFormat(formatStyle)
            val formatDate = sdf.format(date)
            try {
                val var10000 = sdf.parse(formatDate)
                Intrinsics.checkExpressionValueIsNotNull(var10000, "sdf.parse(formatDate)")
                var10000
            } catch (var6: ParseException) {
                var6.printStackTrace()
                Date()
            }
        } else {
            Date()
        }
    }

    /**
     * 日期格式化
     *
     * @param formatStyle
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date(date))

    }
}