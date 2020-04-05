package com.wjx.android.wanandroidmvvm.base.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*
import kotlin.system.exitProcess

/**
 * Created with Android Studio.
 * Description: Activity管理类
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 19:05
 */

class AppManager {
    private val activityStack : Stack<Activity> = Stack()

    companion object {
        val instance by lazy {
            AppManager()
        }
    }

    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

    private fun finishAllActivity() {
        for(activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    }
}