package com.wjx.android.mvvm.common.utils

import android.content.Context
import android.widget.Toast
import java.lang.reflect.ParameterizedType


object CommonUtil {
    fun <T> getClass(t: Any): Class<T> {
        // 通过反射 获取父类泛型 (T) 对应 Class类
        return (t.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0]
                as Class<T>
    }

    fun showToast(context: Context, string : String) {
        Toast.makeText(context, string , Toast.LENGTH_SHORT).show()
    }
}