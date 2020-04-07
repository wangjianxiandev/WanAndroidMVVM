package com.wjx.android.wanandroidmvvm.common.utils

import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/27
 * Time: 18:46
 */
class ChangeThemeEvent {
    fun post() {
        EventBus.getDefault().post(this)
    }
}