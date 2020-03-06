package com.wjx.android.wanandroidmvvm.base

import android.app.Application
import com.kingja.loadsir.core.LoadSir
import com.wjx.android.wanandroidmvvm.base.callback.EmptyCallBack
import com.wjx.android.wanandroidmvvm.base.callback.ErrorCallBack
import com.wjx.android.wanandroidmvvm.base.callback.LoadingCallBack
import com.wjx.android.wanandroidmvvm.base.utils.Preference
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
        Preference.setContext(applicationContext)
        LoadSir.beginBuilder()
            .addCallback(ErrorCallBack())
            .addCallback(LoadingCallBack())
            .addCallback(EmptyCallBack())
            .commit()
    }
}