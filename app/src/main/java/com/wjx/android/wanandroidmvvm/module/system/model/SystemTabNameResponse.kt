package com.wjx.android.wanandroidmvvm.module.system.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/27
 * Time: 17:06
 */
data class SystemTabNameResponse(
    var children : List<SystemLabelResponse>,
    var name : String
)