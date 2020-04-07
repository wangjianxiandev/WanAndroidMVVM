package com.wjx.android.wanandroidmvvm.common.state.callback

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 21:01
 */
interface LoginSuccessListener {
    fun loginSuccess(userName : String, userId : String, collectArticleIds : List<Int>?)
}