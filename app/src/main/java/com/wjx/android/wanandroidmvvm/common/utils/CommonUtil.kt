package com.wjx.android.wanandroidmvvm.common.utils

import android.content.Context
import android.content.Intent
import com.wjx.android.wanandroidmvvm.ui.activity.ArticleDetailActivity
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
        title: String?,
        url: String?
    ) {
        val intent = Intent()
        intent.setClass(context, ArticleDetailActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("url", url)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}