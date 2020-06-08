package com.wjx.android.mvvm

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.wjx.android.mvvm.common.utils.Constant
import com.wjx.android.mvvm.common.utils.SPreference

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/6/8 8:28
 */
open class BaseApplication : Application() {
    companion object {
        lateinit var instance : BaseApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        SPreference.setContext(applicationContext)
    }
}