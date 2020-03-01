package com.wjx.android.wanandroidmvvm.ui.account.data

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:54
 */
data class LoginResponse(
    var icon : String,
    var type : String,
    var collectIds : MutableList<Int>,
    var username : String
)