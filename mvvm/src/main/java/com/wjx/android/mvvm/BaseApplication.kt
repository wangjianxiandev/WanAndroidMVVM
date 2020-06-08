package com.wjx.android.mvvm

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kingja.loadsir.core.LoadSir
import com.wjx.android.wanandroidmvvm.common.utils.Constant
import com.wjx.android.wanandroidmvvm.common.utils.SPreference

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
        initMode()
    }

    private fun initMode() {
        var isNightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
        AppCompatDelegate.setDefaultNightMode(if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}