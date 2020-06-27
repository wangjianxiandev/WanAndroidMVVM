package com.wjx.android.wanandroidmvvm.common.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Vibrator
import com.wjx.android.wanandroidmvvm.module.activity.ArticleDetailActivity
import java.lang.reflect.ParameterizedType


object CommonUtil {
    fun <T> getClass(t: Any): Class<T> {
        // 通过反射 获取父类泛型 (T) 对应 Class类
        return (t.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0]
                as Class<T>
    }

    fun startWebView(
        context: Context,
        url: String,
        title: String
    ) {
        startActivity<ArticleDetailActivity>(context) {
            putExtra("url", url)
            putExtra("title", title)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    /**
     * 设置震动
     *
     * @param context
     * @param milliseconds
     */
    fun Vibrate(context: Context, milliseconds: Long) {
        val vibrator =
            context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(milliseconds)
    }
}