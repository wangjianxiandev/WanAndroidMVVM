package com.wjx.android.wanandroidmvvm.common.utils

import org.greenrobot.eventbus.EventBus

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/28
 * Time: 18:36
 */
class RecreateEvent {
    fun post() {
        EventBus.getDefault().post(this)
    }
}