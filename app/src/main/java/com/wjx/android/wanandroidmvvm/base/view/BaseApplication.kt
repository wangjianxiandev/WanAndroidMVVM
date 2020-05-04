package com.wjx.android.wanandroidmvvm.base.view

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kingja.loadsir.core.LoadSir
import com.tencent.bugly.Bugly
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import com.wjx.android.wanandroidmvvm.common.callback.EmptyCallBack
import com.wjx.android.wanandroidmvvm.common.callback.ErrorCallBack
import com.wjx.android.wanandroidmvvm.common.callback.LoadingCallBack
import com.wjx.android.wanandroidmvvm.common.utils.Constant
import com.wjx.android.wanandroidmvvm.common.utils.SPreference
import org.litepal.LitePal

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/22
 * Time: 14:27
 */
open class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        SPreference.setContext(applicationContext)
        // 集成bugly
        Bugly.init(applicationContext, "7c31dd2361", false)
        ZXingLibrary.initDisplayOpinion(this)
        initMode()
        LoadSir.beginBuilder()
            .addCallback(ErrorCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(EmptyCallBack())
            .commit()
    }

    private fun initMode() {
        var isNightMode: Boolean by SPreference(Constant.NIGHT_MODE, false)
        AppCompatDelegate.setDefaultNightMode(if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}