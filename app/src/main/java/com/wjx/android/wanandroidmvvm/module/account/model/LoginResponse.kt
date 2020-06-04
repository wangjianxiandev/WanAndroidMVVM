package com.wjx.android.wanandroidmvvm.module.account.model

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
    var id : Int,
    var collectIds : List<Int>,
    var username : String
)