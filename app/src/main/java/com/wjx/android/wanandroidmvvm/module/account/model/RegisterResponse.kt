package com.wjx.android.wanandroidmvvm.module.account.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:57
 */
data class RegisterResponse(
    var username : String,
    var id : Int,
    var icon : String,
    var type : Int,
    var collectIds : List<Int>
)